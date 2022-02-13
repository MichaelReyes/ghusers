
package com.technologies.ghusers.core.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.technologies.ghusers.core.data.network.Resource
import com.technologies.ghusers.core.data.network.Status

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.removeObservers(this)
    liveData.observe(this, Observer(body))
}

fun <T> MutableLiveData<T>.updateFields(actions: (MutableLiveData<T>) -> Unit) {
    actions(this)
    this.value = this.value
}

suspend fun <T> Resource<T>.handleResponse(
    onError: (suspend (Resource<T>) -> Unit)? = null,
    onLoading: (suspend (Resource<T>) -> Unit)? = null,
    onSuccess: suspend (Resource<T>) -> Unit,
) {
    when (status) {
        Status.SUCCESS -> {
            onSuccess.invoke(this)
        }
        Status.ERROR -> {
            onError?.invoke(this)
        }
        Status.LOADING -> {
            onLoading?.invoke(this)
        }
    }
}