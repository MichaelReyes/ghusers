package com.technologies.ghusers.core.data.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.technologies.ghusers.core.data.entity.User
import com.technologies.ghusers.core.network.UsersRepository
import kotlinx.coroutines.CoroutineScope


class UsersDataSourceFactory constructor(
    private val scope: CoroutineScope,
    private val repo: UsersRepository,
    private val onLoading: (Boolean) -> Unit,
    private val isEmpty: (Boolean) -> Unit,
    private val onError: ((String?) -> Unit?)? = null
) : DataSource.Factory<Int, User>() {

    val source = MutableLiveData<UsersDataSource>()

    override fun create(): DataSource<Int, User> {
        val source = UsersDataSource(scope, repo, onLoading, isEmpty, onError)
        this.source.postValue(source)
        return source
    }
}
