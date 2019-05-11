package com.android.cuvvatest.repositories.policies

import com.android.cuvvatest.model.CreatedPolicy
import com.android.cuvvatest.model.Policy
import io.reactivex.Observable

interface PolicyRepository {
    fun getPolicy(createdPolicyList: Observable<List<CreatedPolicy>>): Observable<List<Policy>>
}