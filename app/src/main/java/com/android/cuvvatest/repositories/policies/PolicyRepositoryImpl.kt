package com.android.cuvvatest.repositories.policies

import com.android.cuvvatest.model.CreatedPolicy
import com.android.cuvvatest.model.Policy
import com.android.cuvvatest.repositories.policies.cancelled.CancelledPolicyDao
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class PolicyRepositoryImpl(
    private val cancelledPolicyDao: CancelledPolicyDao
) : PolicyRepository {

    override fun getPolicy(createdPolicyList: Observable<List<CreatedPolicy>>): Observable<List<Policy>> {
        return Observable.combineLatest(
            cancelledPolicyDao.getAll(),
            createdPolicyList,
            BiFunction { t1, t2 ->
                t2.map { created ->
                    Policy(
                        created,
                        t1.map { it.policyId }.any {
                            it == created.policyId
                        }
                    )
                }
            }
        )
    }
}