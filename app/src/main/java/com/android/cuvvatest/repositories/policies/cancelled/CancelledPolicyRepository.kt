package com.android.cuvvatest.repositories.policies.cancelled

import com.android.cuvvatest.model.CancelledPolicy
import io.reactivex.Observable

interface CancelledPolicyRepository {
    fun getPolicyById(policyId: String): Observable<CancelledPolicy>

}