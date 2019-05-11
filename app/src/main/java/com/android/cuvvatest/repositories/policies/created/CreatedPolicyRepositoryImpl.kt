package com.android.cuvvatest.repositories.policies.created

import com.android.cuvvatest.model.CreatedPolicy
import com.android.cuvvatest.repositories.policies.cancelled.CancelledPolicyDao
import io.reactivex.Observable

class CreatedPolicyRepositoryImpl(
    private val createdPolicyDao: CreatedPolicyDao,
    private val cancelledPolicyDao: CancelledPolicyDao
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
            cancelledPolicyDao.getPoliciesById(it.policyId)
                .map { cancelledPolicyList ->
                    newList.add(it.toCreatedPolicy())
                }
        }
        return newList
    }
}