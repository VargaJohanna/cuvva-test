package com.android.cuvvatest.repositories.policies.cancelled

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "cancelledPolicyTable")
data class CancelledPolicyEntity (
    @ColumnInfo(name = "policy_id") val policyId: String,
    @ColumnInfo(name = "timestamp") val timestamp: String,
    @PrimaryKey
    @ColumnInfo(name = "unique_key") val uniqueKey: String,
    @ColumnInfo(name = "cancel_type") val cancelType: String,
    @ColumnInfo(name = "updated") val updated: Long
)