package com.android.cuvvatest.di

import com.android.cuvvatest.Constants
import com.android.cuvvatest.network.PolicyDeserializer
import com.android.cuvvatest.network.PolicyService
import com.android.cuvvatest.network.entities.PolicyResponseList
import com.android.cuvvatest.rx.RxSchedulers
import com.android.cuvvatest.rx.RxSchedulersImpl
import com.android.cuvvatest.ui.home.HomeViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { get<Retrofit>().create(PolicyService::class.java) }
    single { RxJava2CallAdapterFactory.create() }
    single {
        GsonBuilder().setLenient().registerTypeAdapter(PolicyResponseList::class.java, PolicyDeserializer()).create()
    }
    single { GsonConverterFactory.create(get<Gson>()) }
    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(Constants.CUVVA_POLICY_BASE_URL)
            .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    factory {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        //For debugging purposes I'm logging the body. It should be removed on production
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpLoggingInterceptor
    }
}

val schedulerModule = module {
    factory<RxSchedulers> { RxSchedulersImpl() }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
}
