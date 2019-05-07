package com.android.cuvvatest.repositories.policies.paid

import androidx.room.*
import io.reactivex.Single
import retrofit2.http.DELETE

@Dao
interface PaidPolicyDao {
    @Insert
    fun insertPaidPolicy(paidPolicyEntity: PaidPolicyEntity)

    @Query("SELECT * FROM paidPolicyTable WHERE policy_id = :policyId")
    fun getPoliciesById(policyId: String): Single<List<PaidPolicyEntity>>

    @Query("DELETE FROM paidPolicyTable")
    fun deleteAll()

    @Transaction
    fun deleteAndInsert(policyList: List<PaidPolicyEntity>) {
        deleteAll()
        policyList.forEach { insertPaidPolicy(it) }
    }
}