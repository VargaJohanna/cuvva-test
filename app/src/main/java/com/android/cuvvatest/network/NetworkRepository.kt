package com.android.cuvvatest.network

import io.reactivex.Completable

interface NetworkRepository {
    fun fetchData(): Completable
}