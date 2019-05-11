package com.android.cuvvatest.repositories.policies

import com.android.cuvvatest.model.CreatedPolicy
import com.android.cuvvatest.model.Policy
import com.android.cuvvatest.repositories.policies.cancelled.CancelledPolicyDao
import io.reactivex.Observable

class PolicyRepositoryImpl(
    private val cancelledPolicyDao: CancelledPolicyDao
) : PolicyRepository {
    override fun getPolicy(createdPolicyList: Observable<List<CreatedPolicy>>): Observable<List<Policy>> {
        return cancelledPolicyDao.getAll()
            .map { policyList ->
                val policyIdList = mutableListOf<String>()
                policyList.forEach { policyIdList.add(it.policyId) }
                policyIdList
            }
            .map { cancelledList ->
                val policyList = mutableListOf<Policy>()
                createdPolicyList.forEach { list ->
                    list.forEach {createdPolicy ->
                        policyList.add(Policy(createdPolicy, cancelledList.any {
                            it == createdPolicy.policyId
                        }))
                    }
                }
                policyList
            }
    }
}