package com.android.cuvvatest.repositories.policies.created

import com.android.cuvvatest.model.CreatedPolicy
import io.reactivex.Observable

interface CreatedPolicyRepository {
    fun getPolicyById(policyId: String): Observable<List<CreatedPolicy>>
}