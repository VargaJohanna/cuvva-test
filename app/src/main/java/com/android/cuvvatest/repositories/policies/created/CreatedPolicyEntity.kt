package com.android.cuvvatest.repositories.policies.created

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "createdPolicyTable")
data class CreatedPolicyEntity(
    @ColumnInfo(name = "policy_id") val policyId: String,
    @ColumnInfo(name = "timestamp") val timestamp: String,
    @ColumnInfo(name = "original_policy_id") val originalPolicyId: String,
    @PrimaryKey
    @ColumnInfo(name = "unique_key") val uniqueKey: String
    ,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "start_date") val startDate: String,
    @ColumnInfo(name = "end_date") val endDate: String,
    @ColumnInfo(name = "updated") val updated: Long
)
