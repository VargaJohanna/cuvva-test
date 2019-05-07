package com.android.cuvvatest.repositories.policies.created

import com.android.cuvvatest.model.CreatedPolicy
import io.reactivex.Observable
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

class CreatedPolicyRepositoryImpl(
    private val createdPolicyDao: CreatedPolicyDao
) : CreatedPolicyRepository {

    override fun getPolicyById(policyId: String): Observable<List<CreatedPolicy>> {
        return createdPolicyDao.getPoliciesById(policyId)
            .map {
                mapCreatedPolicy(it)
            }
    }

    private fun mapCreatedPolicy(list: List<CreatedPolicyEntity>): List<CreatedPolicy> {
        val newList = mutableListOf<CreatedPolicy>()
        list.forEach {
            newList.add(
                CreatedPolicy(
                    policyId = it.policyId,
                    timestamp = it.timestamp,
                    uniqueKey = it.uniqueKey,
                    userId = it.userId,
                    originalPolicyId = it.originalPolicyId,
                    startDate = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(it.startDate),ZoneOffset.UTC),
                    endDate = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(it.endDate),ZoneOffset.UTC),
                    extensionPolicy = it.policyId != it.originalPolicyId,
                    updated = LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(it.updated),ZoneOffset.UTC)
                )
            )
        }
        return newList
    }
}