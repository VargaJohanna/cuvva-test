package com.android.cuvvatest.repositories.policies.paid

import com.android.cuvvatest.model.PaidPolicy
import io.reactivex.Observable

interface PaidPolicyRepository {
    fun getPolicyById(policyId: String): Observable<PaidPolicy>

}