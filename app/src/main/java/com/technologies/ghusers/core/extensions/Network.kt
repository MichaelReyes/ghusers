package com.technologies.ghusers.core.extensions

import com.technologies.ghusers.BuildConfig
import com.technologies.ghusers.core.data.network.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope

@ExperimentalCoroutinesApi
suspend fun <T> ProducerScope<Resource<T>>.safeCall(
    errorHandlingCall: (suspend () -> Unit)? = null, //In case there's an extra handling when receiving error on call
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

            errorHandlingCall?.invoke() ?: kotlin.run {
                send(
                    Resource.error(
                        data = null,
                        message = e.message ?: "Error Occurred!"
                    )
                )
            }

        }
    }
}