package com.android.cuvvatest

import android.app.Application
import com.android.cuvvatest.di.networkModule
import com.android.cuvvatest.di.schedulerModule
import com.android.cuvvatest.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class MyApplication: Application() {
    lateinit var koinApplication: KoinApplication
        private set

    override fun onCreate() {
        super.onCreate()
        koinApplication = startKoin {
            androidContext(this@MyApplication)
            modules(networkModule, schedulerModule, viewModelModule)
        }
    }
}