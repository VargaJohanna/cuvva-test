package com.android.cuvvatest.repositories.policies.created

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.cuvvatest.Constants
import com.android.cuvvatest.model.CreatedPolicy
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

@Entity(tableName = "createdPolicyTable")
data class CreatedPolicyEntity(
    @PrimaryKey
    @ColumnInfo(name = "unique_key") val uniqueKey: String,
    @ColumnInfo(name = "policy_id") val policyId: String,
    @ColumnInfo(name = "timestamp") val timestamp: String,
    @ColumnInfo(name = "original_policy_id") val originalPolicyId: String,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "start_date") val startDate: Long,
    @ColumnInfo(name = "end_date") val endDate: Long,
    @ColumnInfo(name = "updated") val updated: Long,
    @ColumnInfo(name = "vrm") val vrm: String
)

fun CreatedPolicyEntity.toCreatedPolicy(): CreatedPolicy {
    val startDateLocal = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(startDate), ZoneOffset.UTC
    )
    val endDateLocal = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(endDate), ZoneOffset.UTC
    )

    return CreatedPolicy(
        policyId = policyId,
        timestamp = timestamp,
        uniqueKey = uniqueKey,
        userId = userId,
        originalPolicyId = originalPolicyId,
        startDate = startDateLocal,
        endDate = endDateLocal,
        extensionPolicy = policyId != originalPolicyId,
        updated = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(updated), ZoneOffset.UTC),
        active = Constants.CURRENT_DATE < endDateLocal && Constants.CURRENT_DATE > startDateLocal
    )
}

