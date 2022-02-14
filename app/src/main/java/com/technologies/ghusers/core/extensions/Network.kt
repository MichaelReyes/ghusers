package com.technologies.ghusers.core.extensions

import com.technologies.ghusers.BuildConfig
import com.technologies.ghusers.core.data.network.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
suspend fun <T> ProducerScope<Resource<T>>.safeCall(
    call: suspend () -> Unit
): ProducerScope<Resource<T>> {
    return this.apply {
        send(Resource.loading(data = null))
        try {
            call.invoke()
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
            send(
                Resource.error(
                    data = null,
                    message = e.message ?: "Error Occurred!"
                )
            )
        }
    }
}