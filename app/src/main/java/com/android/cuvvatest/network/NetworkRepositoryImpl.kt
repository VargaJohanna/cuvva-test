package com.android.cuvvatest.network

import com.android.cuvvatest.Constants
import com.android.cuvvatest.customException.CustomException
import com.android.cuvvatest.network.entries.PayloadEntry
import com.android.cuvvatest.network.entries.PolicyResponseEntry
import com.android.cuvvatest.network.entries.PolicyResponseList
import com.android.cuvvatest.repositories.policies.cancelled.CancelledPolicyDao
import com.android.cuvvatest.repositories.policies.cancelled.CancelledPolicyEntity
import com.android.cuvvatest.repositories.policies.created.CreatedPolicyDao
import com.android.cuvvatest.repositories.policies.created.CreatedPolicyEntity
import com.android.cuvvatest.repositories.policies.paid.PaidPolicyDao
import com.android.cuvvatest.repositories.policies.paid.PaidPolicyEntity
import com.android.cuvvatest.repositories.vehicle.VehicleDao
import com.android.cuvvatest.repositories.vehicle.VehicleEntity
import io.reactivex.Completable
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Response

class NetworkRepositoryImpl(
    private val service: PolicyService,
    private val vehicleDao: VehicleDao,
    private val cancelledPolicyDao: CancelledPolicyDao,
    private val createdPolicyDao: CreatedPolicyDao,
    private val paidPolicyDao: PaidPolicyDao
    /**
     * This is where the data returned from the network gets inserted to the database.
     * To avoid duplications and always up to date data, the database is cleared first.
     */
) : NetworkRepository {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

    override fun fetchData(): Completable {
        return service.getPolicies()
            .flatMapCompletable {
                if (it.isSuccessful && it.body() != null) {
                    processData(it)
                } else if (it.errorBody() != null) {
                    Completable.error(CustomException(it.message()))
                } else {
                    Completable.error(CustomException(it.message()))
                }
            }
    }

    private fun processData(response: Response<PolicyResponseList>): Completable {
        val vehicleEntityMap = mutableMapOf<String, VehicleEntity>()
        val createdPolicyList = mutableListOf<CreatedPolicyEntity>()
        val paidPolicyList = mutableListOf<PaidPolicyEntity>()
        val cancelledPolicyList = mutableListOf<CancelledPolicyEntity>()

        response.body()!!.responseList.forEach {
            when {
                it.type == Constants.TypeValues.CREATED -> {
                    vehicleEntityMap[mapVehicle(it).vrm] = mapVehicle(it)
                    createdPolicyList.add(mapCreatedPolicy(it))
                }
                it.type == Constants.TypeValues.CANCELLED -> cancelledPolicyList.add(mapCancelledPolicy(it))
                it.type == Constants.TypeValues.PAID -> {
                    paidPolicyList.add(mapPaidPolicy(it))
                }
            }
        }
        return saveDataToDatabase(
            vehicleEntityMap,
            createdPolicyList,
            paidPolicyList,
            cancelledPolicyList
        )
    }

    private fun saveDataToDatabase(
        vehicleEntityMap: Map<String, VehicleEntity>,
        createdPolicyList: List<CreatedPolicyEntity>,
        paidPolicyList: List<PaidPolicyEntity>,
        cancelledPolicyList: List<CancelledPolicyEntity>
    ): Completable {
        return Completable.fromAction {
            vehicleDao.deleteAndInsert(vehicleEntityMap.values.toList())
            createdPolicyDao.deleteAndInsert(createdPolicyList)
            paidPolicyDao.deleteAndInsert(paidPolicyList)
            cancelledPolicyDao.deleteAndInsert(cancelledPolicyList)
        }
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
            startDate = LocalDateTime.parse(responseEntry.payload.startDate, formatter).toEpochSecond(ZoneOffset.UTC),
            endDate = LocalDateTime.parse(responseEntry.payload.endDate, formatter).toEpochSecond(ZoneOffset.UTC),
            updated = System.currentTimeMillis(),
            vrm = responseEntry.payload.vehicle.vrm
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