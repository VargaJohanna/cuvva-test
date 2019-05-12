package com.android.cuvvatest.repositories.vehicle

import com.android.cuvvatest.model.VehicleAndPolicies
import io.reactivex.Observable

interface VehicleAndPoliciesRepository {
    fun getVehiclesAndCreatedPolicies(): Observable<List<VehicleAndPolicies>>
}