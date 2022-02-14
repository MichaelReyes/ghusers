package com.technologies.ghusers.core.network

import android.content.Context
import com.technologies.ghusers.R
import com.technologies.ghusers.core.data.dao.NoteDao
import com.technologies.ghusers.core.data.dao.UserDao
import com.technologies.ghusers.core.data.entity.Note
import com.technologies.ghusers.core.data.entity.User
import com.technologies.ghusers.core.data.network.Resource
import com.technologies.ghusers.core.extensions.safeCall
import com.technologies.ghusers.core.utils.NetworkHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface UsersRepository {

    fun getUsers(start: Int, size: Int): Flow<Resource<List<User>>>
    fun insertUsers(users: List<User>): Flow<Resource<List<User>>>
    fun getUserDetails(userLogin: String): Flow<Resource<Pair<User, Note?>>>
    fun insertNote(note: Note): Flow<Resource<Note>>
    fun searchItems(query: String): Flow<Resource<List<User>>>


    @ExperimentalCoroutinesApi
    class UsersRepositoryImpl
    @Inject constructor(
        private val context: Context,
        private val service: UsersService,
        private val userDao: UserDao,
        private val noteDao: NoteDao,
        private val networkHandler: NetworkHandler
    ) : UsersRepository {
        override fun getUsers(start: Int, size: Int): Flow<Resource<List<User>>> {
            return channelFlow {
                this.safeCall<List<User>>(
                    errorHandlingCall = {
                        val users = userDao.getUsers(size, start)?.map {
                            it.userNote = noteDao.getUserNote(it.id)
                            it
                        }
                        send(
                            Resource.success(
                                data = users ?: emptyList<User>()
                            )
                        )
                    }
                ) {

                    val users = if (networkHandler.isConnected) {
                        service.getUsers(start, size).let {
                            userDao.insert(it)
                            it
                        }
                    } else {
                        userDao.getUsers(size, start) ?: emptyList<User>()
                    }

                    users.map {
                        it.userNote = noteDao.getUserNote(it.id)
                    }

                    send(Resource.success(data = users))
                }
            }.flowOn(Dispatchers.IO).take(2)
        }

        override fun insertUsers(users: List<User>): Flow<Resource<List<User>>> {
            return channelFlow {
                this.safeCall<List<User>> {
                    userDao.insert(users)
                    send(Resource.success(data = users))
                }
            }.flowOn(Dispatchers.IO).take(2)
        }

        override fun getUserDetails(userLogin: String): Flow<Resource<Pair<User, Note?>>> {
            return channelFlow {
                this.safeCall<Pair<User, Note?>>(
                    errorHandlingCall = {
                        val user = userDao.getUserByLogin(userLogin)

                        user?.let {
                            val userNotes = noteDao.getUserNote(it.id)

                            send(Resource.success(data = Pair(it, userNotes)))
                        } ?: kotlin.run {
                            send(
                                Resource.error(
                                    data = null,
                                    context.getString(R.string.error_no_user_details)
                                )
                            )
                        }
                    }
                ) {
                    val user =
                        if (networkHandler.isConnected) {
                            service.getUserDetails(userLogin).let {
                                userDao.insert(it)
                                it
                            }
                        } else {
                            userDao.getUserByLogin(userLogin)
                        }

                    user?.let {
                        val userNotes = noteDao.getUserNote(it.id)

                        send(Resource.success(data = Pair(it, userNotes)))
                    } ?: kotlin.run {
                        send(
                            Resource.error(
                                data = null,
                                context.getString(R.string.error_no_user_details)
                            )
                        )
                    }

                }
            }.flowOn(Dispatchers.IO).take(2)
        }


        override fun insertNote(note: Note): Flow<Resource<Note>> {
            return channelFlow {
                this.safeCall<Note> {
                    noteDao.insert(note)
                    send(Resource.success(data = note))
                }
            }.flowOn(Dispatchers.IO).take(2)
        }

        override fun searchItems(query: String): Flow<Resource<List<User>>> {
            return channelFlow {
                this.safeCall<List<User>> {
                    val users = if (query.isNotBlank()) {
                        val searchResults = userDao.search("%$query%")
                        if (searchResults.isNotEmpty()) {
                            val result = userDao.getUserById(
                                searchResults.map { it.id }
                            )
                            if (!result.isNullOrEmpty()) {
                                result
                            } else {
                                emptyList<User>()
                            }
                        } else {
                            emptyList<User>()
                        }
                    } else {
                        userDao.getUsers()
                    }

                    users.map {
                        it.userNote = noteDao.getUserNote(it.id)
                    }

                    send(Resource.success(data = users))
                }
            }.flowOn(Dispatchers.IO).take(2)
        }
    }
}