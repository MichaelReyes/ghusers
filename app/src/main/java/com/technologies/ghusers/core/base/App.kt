package com.technologies.ghusers.core.base

import android.app.Application
import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@HiltAndroidApp
class App : Application() {

    private val disposables = CompositeDisposable()

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    val internetConnectionStream = MutableSharedFlow<Boolean>()

    override fun onCreate() {
        super.onCreate()

        initNetworkObserver()
    }

    //Haven't found same library with same functions that's why still using this for observing the network
    private fun initNetworkObserver() {

        ReactiveNetwork
            .observeNetworkConnectivity(applicationContext)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                applicationScope.launch {
                    internetConnectionStream.emit(it.available() && it.state() == NetworkInfo.State.CONNECTED)
                }
            }.addTo(disposables)
    }

}