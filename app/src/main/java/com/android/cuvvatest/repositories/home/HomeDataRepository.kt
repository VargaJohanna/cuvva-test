package com.android.cuvvatest.repositories.home

import com.android.cuvvatest.repositories.vehicle.VehicleAndAllCreatedPolicies
import io.reactivex.Observable

interface HomeDataRepository {
    fun getVehiclesAndCreatedPolicies(): Observable<List<VehicleAndAllCreatedPolicies>>
}