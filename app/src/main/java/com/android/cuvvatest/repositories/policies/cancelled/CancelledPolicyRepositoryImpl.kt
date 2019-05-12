package com.android.cuvvatest.repositories.policies.cancelled

import com.android.cuvvatest.model.CancelledPolicy
import io.reactivex.Observable


/**
 * Return a list of CancelledPolicy model object based on the policyId
 */
class CancelledPolicyRepositoryImpl(
    private val cancelledPolicyDao: CancelledPolicyDao
) : CancelledPolicyRepository {
    /**
     * According to the documentation there will be always 1 cancellation for a policy.
     * For this reason I'm taking the first item of the returned list.
     */
    override fun getPolicyById(policyId: String): Observable<CancelledPolicy> {
        return cancelledPolicyDao.getPoliciesById(policyId)
            .map { it.first().toCancelledPolicy() }
    }
}