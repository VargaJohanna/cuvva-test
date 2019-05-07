package com.android.cuvvatest.repositories.policies.created

import androidx.room.*
import com.android.cuvvatest.repositories.policies.EventEntity
import java.util.*

@Entity(tableName = "createdPolicyTable")
data class CreatedPolicyEntity(
    @PrimaryKey
    @ColumnInfo(name = "unique_key") val uniqueKey: String,
    @ColumnInfo(name = "policy_id") val policyId: String,
    @ColumnInfo(name = "timestamp") val timestamp: String,
    @ColumnInfo(name = "original_policy_id") val originalPolicyId: String,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "start_date") val startDate: String,
    @ColumnInfo(name = "end_date") val endDate: String,
    @ColumnInfo(name = "updated") val updated: Long
)
