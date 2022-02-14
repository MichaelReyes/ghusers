package com.technologies.ghusers.core.network

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


    @ExperimentalCoroutinesApi
    class UsersRepositoryImpl
    @Inject constructor(
        private val service: UsersService,
        private val userDao: UserDao,
        private val noteDao: NoteDao,
        private val networkHandler: NetworkHandler
    ) : UsersRepository {
        override fun getUsers(start: Int, size: Int): Flow<Resource<List<User>>> {
            return channelFlow {
                this.safeCall<List<User>> {

                    val users = if (networkHandler.isConnected) {
                        service.getUsers(start, size).let {
                            userDao.insert(it)
                            it
                        }
                    } else {
                        userDao.getUsers(size, start)
                    }

                    users.filter {
                        noteDao.getUserNote(it.id) != null
                    }.map { it.hasNotes = true }

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
                this.safeCall<Pair<User, Note?>> {
                    val user =
                        if (networkHandler.isConnected) {
                            service.getUserDetails(userLogin).let {
                                it
                            }
                        } else {
                            userDao.getUserByLogin(userLogin)
                        }

                    val userNotes = noteDao.getUserNote(user.id)

                    send(
                        Resource.success(data = Pair(user, userNotes))
                    )
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
    }
}