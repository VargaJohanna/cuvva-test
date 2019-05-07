package com.android.cuvvatest.repositories.policies.cancelled

import androidx.room.*
import com.android.cuvvatest.repositories.policies.EventEntity
import java.util.*

@Entity(tableName = "cancelledPolicyTable")
data class CancelledPolicyEntity (
    @PrimaryKey
    @ColumnInfo(name = "unique_key") val uniqueKey: String,
    @ColumnInfo(name = "policy_id") val policyId: String,
    @ColumnInfo(name = "timestamp") val timestamp: String,
    @ColumnInfo(name = "cancel_type") val cancelType: String,
    @ColumnInfo(name = "updated") val updated: Long
)