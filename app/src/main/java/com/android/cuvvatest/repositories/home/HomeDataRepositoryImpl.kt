package com.android.cuvvatest.repositories.home

import com.android.cuvvatest.repositories.vehicle.VehicleAndAllCreatedPolicies
import com.android.cuvvatest.repositories.vehicle.VehicleDao
import io.reactivex.Observable

class HomeDataRepositoryImpl(
    private val vehicleDao: VehicleDao
) : HomeDataRepository {
    override fun getVehiclesAndCreatedPolicies(): Observable<List<VehicleAndAllCreatedPolicies>> {
        return vehicleDao.loadVehicleAndEvents()
    }
}