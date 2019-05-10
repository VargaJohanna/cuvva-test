package com.android.cuvvatest.repositories.policies.cancelled

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.cuvvatest.model.CancelledPolicy
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

@Entity(tableName = "cancelledPolicyTable")
data class CancelledPolicyEntity(
    @PrimaryKey
    @ColumnInfo(name = "unique_key") val uniqueKey: String,
    @ColumnInfo(name = "policy_id") val policyId: String,
    @ColumnInfo(name = "timestamp") val timestamp: String,
    @ColumnInfo(name = "cancel_type") val cancelType: String,
    @ColumnInfo(name = "updated") val updated: Long
)

fun CancelledPolicyEntity.toCancelledPolicy() = CancelledPolicy(
    policyId = policyId,
    timestamp = timestamp,
    uniqueKey = uniqueKey,
    cancelType = cancelType,
    updated = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(updated), ZoneOffset.UTC)
)