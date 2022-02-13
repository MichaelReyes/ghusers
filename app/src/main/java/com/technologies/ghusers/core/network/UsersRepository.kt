package com.technologies.ghusers.core.network

import com.technologies.ghusers.BuildConfig
import com.technologies.ghusers.core.data.dao.UserDao
import com.technologies.ghusers.core.data.entity.User
import com.technologies.ghusers.core.data.network.Resource
import com.technologies.ghusers.core.utils.NetworkHandler
import io.reactivex.Completable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.lang.Exception
import javax.inject.Inject

interface UsersRepository {

    fun getUsers(start: Int, size: Int): Flow<Resource<List<User>>>
    fun insertUsers(users: List<User>): Flow<Resource<List<User>>>


    @ExperimentalCoroutinesApi
    class UsersRepositoryImpl
    @Inject constructor(
        private val service: UsersService,
        private val userDao: UserDao,
        private val networkHandler: NetworkHandler
    ) : UsersRepository {
        override fun getUsers(start: Int, size: Int): Flow<Resource<List<User>>> {
            return channelFlow {
                send(Resource.loading(data = null))
                try {
                    if (networkHandler.isConnected) {
                        service.getUsers(start, size).let {
                            userDao.insert(it)
                            send(Resource.success(data = it))
                        }
                    } else {
                        send(
                            Resource.success(
                                data = userDao.getUsers(size, start)
                            )
                        )
                    }
                } catch (exception: Exception) {
                    if (BuildConfig.DEBUG) {
                        exception.printStackTrace()
                    }
                    send(
                        Resource.error(
                            data = null, message = exception.message ?: "Error Occurred!"
                        )
                    )
                }

            }.flowOn(Dispatchers.IO).take(2)
        }

        override fun insertUsers(users: List<User>): Flow<Resource<List<User>>> {
            return flow {
                userDao.insert(users)
                emit(Resource.success(data = users))
            }.flowOn(Dispatchers.IO)
        }
    }
}