package com.technologies.ghusers.feature.users

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.technologies.ghusers.core.base.BaseViewModel
import com.technologies.ghusers.core.data.entity.User
import com.technologies.ghusers.core.data.source.UsersDataSourceFactory
import com.technologies.ghusers.core.extensions.handleResponse
import com.technologies.ghusers.core.network.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class UsersMainViewModel @Inject constructor() : BaseViewModel()