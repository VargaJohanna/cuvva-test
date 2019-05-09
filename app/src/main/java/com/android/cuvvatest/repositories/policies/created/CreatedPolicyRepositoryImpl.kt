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
            val startDate = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(it.startDate),ZoneOffset.UTC)
            val endDate = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(it.endDate),ZoneOffset.UTC)
            newList.add(it.toCreatedPolicy())
        }
        return newList
    }
}