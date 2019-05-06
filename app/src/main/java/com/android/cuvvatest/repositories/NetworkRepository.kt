package com.android.cuvvatest.repositories

import io.reactivex.Completable

interface NetworkRepository {
    fun fetchVehicles(): Completable
}