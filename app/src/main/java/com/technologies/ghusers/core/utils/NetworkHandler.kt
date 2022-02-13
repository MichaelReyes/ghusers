package com.technologies.ghusers.core.utils

import android.content.Context
import com.technologies.ghusers.core.extensions.hasActiveNetwork
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler
@Inject constructor(@ApplicationContext val context: Context) {
    val isConnected get() = context.hasActiveNetwork()
}