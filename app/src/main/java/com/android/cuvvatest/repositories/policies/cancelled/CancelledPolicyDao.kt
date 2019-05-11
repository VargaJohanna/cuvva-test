package com.android.cuvvatest.repositories.policies.cancelled

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Observable

@Dao
interface CancelledPolicyDao {
    @Insert
    fun insertCancelledPolicy(cancelledPolicyEntity: CancelledPolicyEntity)

    @Query("SELECT * FROM cancelledPolicyTable WHERE policy_id = :policyId")
    fun getPoliciesById(policyId: String): Observable<List<CancelledPolicyEntity>>

    @Query("SELECT * FROM cancelledPolicyTable")
    fun getAll(): Observable<List<CancelledPolicyEntity>>

    @Query("DELETE FROM cancelledPolicyTable")
    fun deleteAll()

    @Transaction
    fun deleteAndInsert(policyList: List<CancelledPolicyEntity>) {
        deleteAll()
        policyList.forEach { insertCancelledPolicy(it) }
    }
}