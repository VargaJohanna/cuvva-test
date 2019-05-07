package com.android.cuvvatest.repositories.vehicle

import io.reactivex.Single

class VehicleRepositoryImpl(
    private val vehicleDao: VehicleDao
) : VehicleRepository {
    override fun getAllVehicles(): Single<List<VehicleAndAllPolicyIds>> {
        return vehicleDao.loadVehicleAndEvents()
    }

}