package com.android.cuvvatest.repositories.policies.cancelled

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface CancelledPolicyDao {
    @Insert
    fun insertCancelledPolicy(cancelledPolicyEntity: CancelledPolicyEntity)

    @Query("SELECT * FROM cancelledPolicyTable WHERE policy_id = :policyId")
    fun getPoliciesById(policyId: String): Single<List<CancelledPolicyEntity>>
}