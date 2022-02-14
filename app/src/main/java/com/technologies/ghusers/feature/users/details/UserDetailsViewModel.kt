package com.technologies.ghusers.feature.users.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.technologies.ghusers.core.base.BaseViewModel
import com.technologies.ghusers.core.data.entity.Note
import com.technologies.ghusers.core.data.entity.User
import com.technologies.ghusers.core.extensions.handleResponse
import com.technologies.ghusers.core.extensions.updateFields
import com.technologies.ghusers.core.network.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@FlowPreview
@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _currentNotes = MutableLiveData<Note>()
    val currentNotes: LiveData<Note> = _currentNotes

    private val _formState = MutableLiveData(DetailsFormState())
    val formState: LiveData<DetailsFormState> = _formState

    private val _form = MutableLiveData<DetailsForm>()
    val form: LiveData<DetailsForm> = _form

    private val dataStreamFlow = MutableSharedFlow<String>()

    private val formObserver = Observer<DetailsForm> { form ->
        if (form != null) {
            _formState.updateFields {
                it.value?.let { state ->
                    state.isDataValid =
                        form.notes?.isNotEmpty() == true &&
                                form.notes != currentNotes.value?.notes
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            observeDataStream()
        }

        form.observeForever(formObserver)
    }

    private suspend fun observeDataStream() {
        dataStreamFlow.debounce(500)
            .collect { data ->
                withContext(Dispatchers.Main) {
                    _form.updateFields {
                        if (it.value == null) {
                            it.value = DetailsForm(null)
                        }
                        it.value?.notes = data
                    }
                }
            }
    }

    //Send text changes to SharedFlow
    fun onDataChange(text: CharSequence) {
        viewModelScope.launch {
            dataStreamFlow.emit(text.toString())
        }
    }

    fun getUserDetails(userLogin: String) {
        runBlocking {
            usersRepository
                .getUserDetails(userLogin)
        }.onEach { resource ->
            resource.handleResponse(
                onSuccess = {
                    withContext(Dispatchers.Main) {
                        it.data?.let { pair ->
                            _user.postValue(pair.first)
                            pair.second?.let { notes ->
                                _currentNotes.value = notes
                            }
                        }
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


    fun insertNotes() {
        _form.value?.let { form ->
            runBlocking {
                usersRepository.insertNote(
                    Note(
                        notes = form.notes ?: "",
                        userId = _user.value?.id ?: 0
                    )
                )
            }.onEach { resource ->
                resource.handleResponse {
                    withContext(Dispatchers.Main) {
                        it.data?.let { note ->
                            _currentNotes.value = note
                            formObserver.onChanged(form)
                        }
                        _formState.updateFields {
                            it.value?.onNotesSaved = true
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun resetNotesSaved() {
        _formState.updateFields {
            it.value?.onNotesSaved = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        form.removeObserver(formObserver)
    }
}