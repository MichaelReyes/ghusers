package com.technologies.ghusers.core.data.source

import androidx.paging.ItemKeyedDataSource
import com.technologies.ghusers.core.data.entity.User
import com.technologies.ghusers.core.extensions.handleResponse
import com.technologies.ghusers.core.network.UsersRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class UsersDataSource(
    private val scope: CoroutineScope,
    private val repository: UsersRepository,
    private val onLoading: (Boolean) -> Unit,
    private val isEmpty: (Boolean) -> Unit,
    private val onError: ((String?) -> Unit?)? = null
) : ItemKeyedDataSource<Int, User>() {

    private var sinceCount = 0
    private var size = 5

    var params: LoadParams<Int>? = null
    var callback: LoadCallback<User>? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<User>
    ) {
        onLoading.invoke(true)
        scope.launch {
            val response = runBlocking {
                delay(3000) //Added delay to show the effect of list skeleton layout
                repository.getUsers(sinceCount, size)
            }
            response.collect { resource ->
                withContext(Dispatchers.Main) {
                    resource.handleResponse(
                        onError = {
                            onLoading.invoke(false)
                            onCallError(it.message)
                        },
                        onLoading = {
                            onLoading.invoke(true)
                        },
                        onSuccess = {
                            onLoading.invoke(false)
                            it.data?.let { users ->
                                onUsersFetched(users, callback)
                            } ?: onCallError("Users list is empty")
                        }
                    )
                }

            }

        }
    }

    private fun onUsersFetched(users: List<User>, callback: LoadInitialCallback<User>) {
        isEmpty.invoke(users.isEmpty())
        if (users.isNotEmpty()) {
            scope.launch { repository.insertUsers(users) }
        }
        callback.onResult(users)
    }

    private fun onCallError(error: String?) {
        onError?.invoke(error)
        isEmpty.invoke(true)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<User>) {
        this.params = params
        this.callback = callback

        scope.launch {
            val response = runBlocking {
                repository.getUsers(
                    if (params.key == sinceCount) {
                        sinceCount + size + 1
                    } else {
                        params.key
                    }, size
                )
            }
            response.collect { resource ->
                withContext(Dispatchers.Main) {
                    resource.handleResponse(
                        onError = {
                            onLoading.invoke(false)
                            onPaginationError(it.message)
                        },
                        onLoading = {
                            onLoading.invoke(true)
                        },
                        onSuccess = {
                            onLoading.invoke(false)
                            it.data?.let { users ->
                                onMoreUsersFetched(users, callback)
                            } ?: onCallError("Users list is empty")
                        }
                    )
                }
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<User>) {}

    override fun getKey(item: User): Int = sinceCount

    private fun onMoreUsersFetched(users: List<User>, callback: LoadCallback<User>) {
        sinceCount += if (sinceCount == 0) {
            size + 1
        } else {
            size
        }
        if (users.isNotEmpty()) {
            scope.launch { repository.insertUsers(users) }
        }
        callback.onResult(users)
    }

    private fun onPaginationError(error: String?) {
        onError?.invoke(error)
    }

    fun clear() {
        sinceCount = 0
    }
}
