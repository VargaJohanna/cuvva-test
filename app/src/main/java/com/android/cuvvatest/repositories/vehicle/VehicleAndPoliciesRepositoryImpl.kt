package com.android.cuvvatest.repositories.vehicle

import com.android.cuvvatest.model.VehicleAndPolicies
import io.reactivex.Observable

/**
 * Return a list of VehicleAndPolicies model object.
 * Like this I can easily display and count all the policies relevant for a vehicle.
 */
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