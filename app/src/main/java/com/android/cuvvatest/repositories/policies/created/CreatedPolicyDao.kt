package com.android.cuvvatest.repositories.policies.created

import androidx.room.*
import io.reactivex.Single

@Dao
interface CreatedPolicyDao {
    @Insert
    fun insertCreatedPolicy(createdPolicyEntity: CreatedPolicyEntity)

    @Query("SELECT * FROM createdPolicyTable WHERE original_policy_id = :policyId")
    fun getPoliciesById(policyId: String): Single<List<CreatedPolicyEntity>>

    @Query("DELETE FROM createdPolicyTable")
    fun deleteAll()

    @Transaction
    fun deleteAndInsert(policyList: List<CreatedPolicyEntity>) {
        deleteAll()
        policyList.forEach { insertCreatedPolicy(it) }
    }
}