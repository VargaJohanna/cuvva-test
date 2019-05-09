package com.android.cuvvatest.repositories.home

import com.android.cuvvatest.Constants
import com.android.cuvvatest.model.HomeDataObject
import com.android.cuvvatest.repositories.policies.created.toCreatedPolicy
import com.android.cuvvatest.repositories.vehicle.VehicleDao
import com.android.cuvvatest.repositories.vehicle.toVehicle
import io.reactivex.Observable
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit

class HomeDataRepositoryImpl(
    private val vehicleDao: VehicleDao
) : HomeDataRepository {
    override fun getVehiclesAndCreatedPolicies(): Observable<List<HomeDataObject>> {
        return vehicleDao.loadVehicleAndEvents()
            .map { list ->
                val homeDataList = mutableListOf<HomeDataObject>()
                list.forEach { vehicleAndAllCreatedPolicies ->
                    val createdPolicyList = vehicleAndAllCreatedPolicies.createdPolicyList.map {
                        it.toCreatedPolicy()
                    }
                    var remainingTime = 0L
                    if(createdPolicyList.any{it.active}) {
                        val activePolicy = createdPolicyList.first { it.active }
                        val fromTemp = LocalDateTime.from(Constants.CURRENT_DATE)
                        remainingTime = fromTemp.until(activePolicy.endDate, ChronoUnit.MINUTES)

                    }

                    homeDataList.add(
                        HomeDataObject(
                            vehicle = vehicleAndAllCreatedPolicies.vehicleEntity[0].toVehicle(),
                            createdPolicyList = createdPolicyList,
                            updated = LocalDateTime.now(),
                            hasActive = createdPolicyList.any { it.active },
                            remainingTimeOfActive = remainingTime
                        )
                    )
                }
                homeDataList
            }
    }
}