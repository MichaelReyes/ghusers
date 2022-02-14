package com.technologies.ghusers.feature.users.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.technologies.ghusers.core.base.BaseViewModel
import com.technologies.ghusers.core.data.entity.User
import com.technologies.ghusers.core.data.source.UsersDataSourceFactory
import com.technologies.ghusers.core.extensions.handleResponse
import com.technologies.ghusers.core.network.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@FlowPreview
@HiltViewModel
class SearchViewModel @Inject constructor(
    val usersRepository: UsersRepository
) : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _search = MutableLiveData("")
    val search: LiveData<String> = _search

    private val searchStreamFlow = MutableSharedFlow<String>()

    private val searchObserver = Observer<String> {
        if (it != null) {
            usersRepository.searchItems(it)
                .onEach { resource ->
                    resource.handleResponse {
                        withContext(Dispatchers.Main) {
                            it.data?.let { users ->
                                _users.value = users
                            } ?: kotlin.run {
                                _users.value = emptyList()
                            }
                        }
                    }
                    setLoading(false)
                }.launchIn(viewModelScope)
        }
    }

    init {
        viewModelScope.launch {
            observeSearchStream()
        }

        search.observeForever(searchObserver)
    }

    private suspend fun observeSearchStream() {
        searchStreamFlow.debounce(500)
            .collect { data ->
                withContext(Dispatchers.Main) {
                    _search.value = data
                }
            }
    }

    //Send text changes to SharedFlow
    fun onSearchChange(text: CharSequence) {
        viewModelScope.launch {
            searchStreamFlow.emit(text.toString())
        }
    }
}