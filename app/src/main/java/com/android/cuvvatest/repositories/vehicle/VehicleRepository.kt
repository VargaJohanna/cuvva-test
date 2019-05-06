package com.android.cuvvatest.repositories.vehicle

import io.reactivex.Single

interface VehicleRepository {
    fun getAllVehicles(): Single<List<VehicleAndAllPolicyIds>>
}