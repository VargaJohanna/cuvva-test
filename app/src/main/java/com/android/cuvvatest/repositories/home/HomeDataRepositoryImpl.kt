package com.android.cuvvatest.repositories.home

import com.android.cuvvatest.model.HomeDataObject
import com.android.cuvvatest.repositories.policies.created.toCreatedPolicy
import com.android.cuvvatest.repositories.vehicle.VehicleDao
import com.android.cuvvatest.repositories.vehicle.toVehicle
import io.reactivex.Observable
import org.threeten.bp.LocalDateTime

class HomeDataRepositoryImpl(
    private val vehicleDao: VehicleDao
) : HomeDataRepository {
    override fun getVehiclesAndCreatedPolicies(): Observable<List<HomeDataObject>> {
        return vehicleDao.loadVehicleAndEvents()
            .map { list ->
                val homeDataList = mutableListOf<HomeDataObject>()
                list.forEach { vehicleAndAllCreatedPolicies ->
                    homeDataList.add(
                        HomeDataObject(
                            vehicle = vehicleAndAllCreatedPolicies.vehicleEntity[0].toVehicle(),
                            createdPolicyList = vehicleAndAllCreatedPolicies.createdPolicyList.map {
                                it.toCreatedPolicy()
                            },
                            updated = LocalDateTime.now()
                        )
                    )
                }
                homeDataList
            }
    }
}