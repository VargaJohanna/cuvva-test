package com.android.cuvvatest

import android.app.Application
import com.android.cuvvatest.di.networkModule
import com.android.cuvvatest.di.repositoryModule
import com.android.cuvvatest.di.schedulerModule
import com.android.cuvvatest.di.viewModelModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class MyApplication: Application() {
    lateinit var koinApplication: KoinApplication
        private set

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        koinApplication = startKoin {
            androidContext(this@MyApplication)
            modules(networkModule, schedulerModule, viewModelModule, repositoryModule)
        }
    }
}