package com.android.cuvvatest.repositories

import com.android.cuvvatest.Constants
import com.android.cuvvatest.network.PolicyService
import com.android.cuvvatest.network.entries.PayloadEntry
import com.android.cuvvatest.network.entries.PolicyResponseEntry
import com.android.cuvvatest.repositories.policies.EventEntity
import com.android.cuvvatest.repositories.policies.cancelled.CancelledPolicyEntity
import com.android.cuvvatest.repositories.policies.created.CreatedPolicyEntity
import com.android.cuvvatest.repositories.policies.paid.PaidPolicyEntity
import com.android.cuvvatest.repositories.vehicle.VehicleEntity
import io.reactivex.Completable

class NetworkRepositoryImpl(
    private val service: PolicyService
) : NetworkRepository {

    override fun fetchVehicles(): Completable {
        val vehicleEntityList = mutableListOf<VehicleEntity>()
        val eventEntityList = mutableListOf<EventEntity>()
        val createdPolicyList = mutableListOf<CreatedPolicyEntity>()
        val paidPolicyList = mutableListOf<PaidPolicyEntity>()
        val cancelledPolicyList = mutableListOf<CancelledPolicyEntity>()
        return service.getPolicies()
            .map { response ->
                if (response.isSuccessful && response.body() != null) {
                    response.body()!!.responseList.forEach {
                        when {
                            it.type == Constants.TypeValues.CREATED -> {
                                vehicleEntityList.add(mapVehicle(it))
                                createdPolicyList.add(mapCreatedPolicy(it))
                                eventEntityList.add(mapEventEntity(it))
                            }
                            it.type == Constants.TypeValues.CANCELLED -> cancelledPolicyList.add(mapCancelledPolicy(it))
                            it.type == Constants.TypeValues.PAID -> {
                                paidPolicyList.add(mapPaidPolicy(it))
                            }
                        }
                    }
                }
            }
            .doOnSuccess {
                //??
            }
            .ignoreElement()
    }

    private fun mapVehicle(responseEntry: PolicyResponseEntry): VehicleEntity {
        responseEntry.payload as PayloadEntry.PayloadCreated
        return VehicleEntity(
            vrm = responseEntry.payload.vehicle.vrm,
            prettyVrm = responseEntry.payload.vehicle.prettyVrm,
            make = responseEntry.payload.vehicle.make,
            model = responseEntry.payload.vehicle.model,
            color = responseEntry.payload.vehicle.color,
            updated = System.currentTimeMillis()
        )
    }

    private fun mapCreatedPolicy(responseEntry: PolicyResponseEntry): CreatedPolicyEntity {
        responseEntry.payload as PayloadEntry.PayloadCreated
        return CreatedPolicyEntity(
            policyId = responseEntry.payload.policyId,
            originalPolicyId = responseEntry.payload.originalPolicyId,
            timestamp = responseEntry.timestamp,
            uniqueKey = responseEntry.uniqueKey,
            userId = responseEntry.payload.userId,
            startDate = responseEntry.payload.startDate,
            endDate = responseEntry.payload.endDate,
            updated = System.currentTimeMillis()
        )
    }

    private fun mapEventEntity(responseEntry: PolicyResponseEntry): EventEntity {
        responseEntry.payload as PayloadEntry.PayloadCreated
        return EventEntity(
            policyId = responseEntry.payload.policyId,
            vehicleVrm = responseEntry.payload.vehicle.vrm
        )
    }

    private fun mapCancelledPolicy(responseEntry: PolicyResponseEntry): CancelledPolicyEntity {
        responseEntry.payload as PayloadEntry.PayloadCancelled
        return CancelledPolicyEntity(
            policyId = responseEntry.payload.policyId,
            timestamp = responseEntry.timestamp,
            uniqueKey = responseEntry.uniqueKey,
            cancelType = responseEntry.type,
            updated = System.currentTimeMillis()
        )
    }

    private fun mapPaidPolicy(responseEntry: PolicyResponseEntry): PaidPolicyEntity {
        responseEntry.payload as PayloadEntry.PayloadPaid
        return PaidPolicyEntity(
            policyId = responseEntry.payload.policyId,
            timestamp = responseEntry.timestamp,
            uniqueKey = responseEntry.uniqueKey,
            underwriterPremium = responseEntry.payload.pricing.underwriterPremium,
            commission = responseEntry.payload.pricing.commission,
            totalPremium = responseEntry.payload.pricing.totalPremium,
            ipt = responseEntry.payload.pricing.ipt,
            iptRate = responseEntry.payload.pricing.iptRate,
            extraFees = responseEntry.payload.pricing.extraFees,
            vat = responseEntry.payload.pricing.vat,
            deductions = responseEntry.payload.pricing.deductions,
            totalPayable = responseEntry.payload.pricing.totalPayable,
            updated = System.currentTimeMillis()
        )
    }
}