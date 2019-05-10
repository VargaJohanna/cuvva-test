package com.android.cuvvatest.repositories.policies.cancelled

import com.android.cuvvatest.model.CancelledPolicy
import io.reactivex.Observable

class CancelledPolicyRepositoryImpl(
    private val cancelledPolicyDao: CancelledPolicyDao
) : CancelledPolicyRepository {

    override fun getPolicyById(policyId: String): Observable<CancelledPolicy> {
        return cancelledPolicyDao.getPoliciesById(policyId)
            .map {
                it.first().toCancelledPolicy()
            }
    }
}