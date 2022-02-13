package com.technologies.ghusers.feature.users.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.technologies.ghusers.core.base.BaseViewModel
import com.technologies.ghusers.core.data.entity.User
import com.technologies.ghusers.core.extensions.handleResponse
import com.technologies.ghusers.core.network.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun getUserDetails(userLogin: String) {
        usersRepository
            .getUserDetails(userLogin)
            .onEach { resource ->
                resource.handleResponse(
                    onSuccess = {
                        it.data?.let { user ->
                            _user.postValue(user)
                        }
                        setLoading(false)
                    },
                    onLoading = {
                        setLoading(true)
                    },
                    onError = {
                        it.message?.let { error ->
                            setError(error)
                        }
                        setLoading(false)
                    }
                )
            }.launchIn(viewModelScope)
    }
}