package com.android.cuvvatest.repositories.policies.created

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface CreatedPolicyDao {
    @Insert
    fun insertCreatedPolicy(createdPolicyEntity: CreatedPolicyEntity)

    @Query("SELECT * FROM createdPolicyTable WHERE original_policy_id = :policyId")
    fun getPoliciesById(policyId: String): Single<List<CreatedPolicyEntity>>
}