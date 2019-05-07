package com.android.cuvvatest.repositories.policies.created

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Observable

@Dao
interface CreatedPolicyDao {
    @Insert
    fun insertCreatedPolicy(createdPolicyEntity: CreatedPolicyEntity)

    @Query("SELECT * FROM createdPolicyTable WHERE original_policy_id = :policyId")
    fun getPoliciesById(policyId: String): Observable<List<CreatedPolicyEntity>>

    @Query("DELETE FROM createdPolicyTable")
    fun deleteAll()

    @Transaction
    fun deleteAndInsert(policyList: List<CreatedPolicyEntity>) {
        deleteAll()
        policyList.forEach { insertCreatedPolicy(it) }
    }

    @Query("SELECT * FROM createdPolicyTable WHERE start_date < :startDate AND end_date > :endDate AND vrm = :vrm")
    fun getAllActivePolicyByVehicle(vrm: String, startDate: Long, endDate: Long): Observable<List<CreatedPolicyEntity>>
}