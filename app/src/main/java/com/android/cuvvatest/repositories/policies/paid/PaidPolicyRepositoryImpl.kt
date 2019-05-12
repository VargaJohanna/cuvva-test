package com.android.cuvvatest.repositories.policies.paid

import com.android.cuvvatest.model.PaidPolicy
import io.reactivex.Observable

class PaidPolicyRepositoryImpl(
    private val paidPolicyDao: PaidPolicyDao
) : PaidPolicyRepository {

    override fun getPolicyById(policyId: String): Observable<List<PaidPolicy>> {
        val newList = mutableListOf<PaidPolicy>()
        return paidPolicyDao.getPoliciesById(policyId)
            .map {
                it.forEach {
                    newList.add(it.toPaidPolicy())
                }
                newList
            }
    }
}