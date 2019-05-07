package com.android.cuvvatest.repositories.policies.cancelled

import androidx.room.*
import io.reactivex.Single

@Dao
interface CancelledPolicyDao {
    @Insert
    fun insertCancelledPolicy(cancelledPolicyEntity: CancelledPolicyEntity)

    @Query("SELECT * FROM cancelledPolicyTable WHERE policy_id = :policyId")
    fun getPoliciesById(policyId: String): Single<List<CancelledPolicyEntity>>

    @Query("DELETE FROM cancelledPolicyTable")
    fun deleteAll()

    @Transaction
    fun deleteAndInsert(policyList: List<CancelledPolicyEntity>) {
        deleteAll()
        policyList.forEach { insertCancelledPolicy(it) }
    }
}