package com.technologies.ghusers.feature.users.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.technologies.ghusers.core.base.BaseViewModel
import com.technologies.ghusers.core.data.entity.User
import com.technologies.ghusers.core.data.source.UsersDataSourceFactory
import com.technologies.ghusers.core.network.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UsersViewModel @Inject constructor(
    val usersRepository: UsersRepository
) : BaseViewModel() {

    lateinit var sourceFactory: UsersDataSourceFactory

    private var _empty = MutableLiveData(true)
    var empty: LiveData<Boolean> = _empty

    var users: LiveData<PagedList<User>> = MutableLiveData()

    fun getUsers() {
        val config = PagedList.Config.Builder()
            .setPageSize(5)
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5)
            .build()

        sourceFactory = UsersDataSourceFactory(viewModelScope, usersRepository,
            onLoading = {
                setLoading(it)
            }, isEmpty = {
                _empty.postValue(it)
            }
        ) { error ->
            error?.let { setError(it) }
        }

        users = LivePagedListBuilder(sourceFactory, config).build()
    }

    fun reload() {
        if (::sourceFactory.isInitialized) {
            sourceFactory.source.value?.invalidate()
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}