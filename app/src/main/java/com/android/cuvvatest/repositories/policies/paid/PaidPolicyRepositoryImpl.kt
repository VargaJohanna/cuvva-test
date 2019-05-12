package com.android.cuvvatest.repositories.policies.paid

import com.android.cuvvatest.model.PaidPolicy
import io.reactivex.Observable

/**
 * Return a list of PaidPolicy model object based on the policyId
 */
class PaidPolicyRepositoryImpl(
    private val paidPolicyDao: PaidPolicyDao
) : PaidPolicyRepository {

    override fun getPolicyById(policyId: String): Observable<List<PaidPolicy>> {
        return paidPolicyDao.getPoliciesById(policyId)
            .map { list -> list.map { it.toPaidPolicy() } }
    }
}