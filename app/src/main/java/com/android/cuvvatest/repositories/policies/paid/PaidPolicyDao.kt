package com.android.cuvvatest.repositories.policies.paid

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Observable

@Dao
interface PaidPolicyDao {
    @Insert
    fun insertPaidPolicy(paidPolicyEntity: PaidPolicyEntity)

    @Query("SELECT * FROM paidPolicyTable WHERE policy_id = :policyId")
    fun getPoliciesById(policyId: String): Observable<List<PaidPolicyEntity>>

    @Query("DELETE FROM paidPolicyTable")
    fun deleteAll()

    @Transaction
    fun deleteAndInsert(policyList: List<PaidPolicyEntity>) {
        deleteAll()
        policyList.forEach { insertPaidPolicy(it) }
    }
}