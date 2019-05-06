package com.android.cuvvatest.repositories.vehicle

import com.android.cuvvatest.repositories.policies.EventDao
import com.android.cuvvatest.repositories.policies.cancelled.CancelledPolicyDao
import com.android.cuvvatest.repositories.policies.created.CreatedPolicyDao
import com.android.cuvvatest.repositories.policies.paid.PaidPolicyDao
import io.reactivex.Single

class VehicleRepositoryImpl(
    private val vehicleDao: VehicleDao,
    private val paidPolicyDao: PaidPolicyDao,
    private val createdPolicyDao: CreatedPolicyDao,
    private val cancelledPolicyDao: CancelledPolicyDao,
    private val eventDao: EventDao
) : VehicleRepository {
    override fun getAllVehicles(): Single<List<VehicleAndAllPolicyIds>> {
        return vehicleDao.loadVehicleAndEvents()
    }

}