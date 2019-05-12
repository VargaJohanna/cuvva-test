package com.android.cuvvatest.repositories.vehicle

import com.android.cuvvatest.model.VehicleAndPolicies
import io.reactivex.Observable

class VehicleAndPoliciesRepositoryImpl(
    private val vehicleDao: VehicleDao
) : VehicleAndPoliciesRepository {

    override fun getVehiclesAndCreatedPolicies(): Observable<List<VehicleAndPolicies>> {
        return vehicleDao.loadVehicleAndEvents()
            .map { list ->
                list.map { it.toVehicleAndPolicies() }
            }
    }
}