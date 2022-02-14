
package com.technologies.ghusers.core.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.technologies.ghusers.core.data.network.Resource
import com.technologies.ghusers.core.data.network.Status
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

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

fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }

    this.observeForever(observer)

    // Don't wait indefinitely if the LiveData is not set.
    if (!latch.await(time, timeUnit)) {
        throw TimeoutException("LiveData value was never set.")
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}