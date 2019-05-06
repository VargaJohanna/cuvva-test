package com.android.cuvvatest.repositories.policies.paid

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface PaidPolicyDao {
    @Insert
    fun insertPaidPolicy(paidPolicyEntity: PaidPolicyEntity)

    @Query("SELECT * FROM paidPolicyTable WHERE policy_id = :policyId")
    fun getPoliciesById(policyId: String): Single<List<PaidPolicyEntity>>
}