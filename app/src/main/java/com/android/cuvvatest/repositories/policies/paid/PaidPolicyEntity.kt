package com.android.cuvvatest.repositories.policies.paid

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "paidPolicyTable")
data class PaidPolicyEntity(
    @PrimaryKey
    @ColumnInfo(name = "unique_key") val uniqueKey: String,
    @ColumnInfo(name = "policy_id") val policyId: String,
    @ColumnInfo(name = "timestamp") val timestamp: String,
    @ColumnInfo(name = "underwriter_premium") val underwriterPremium: Float,
    @ColumnInfo(name = "commission") val commission: Float,
    @ColumnInfo(name = "total_premium") val totalPremium: Float,
    @ColumnInfo(name = "ipt") val ipt: Float,
    @ColumnInfo(name = "ipt_rate") val iptRate: Float,
    @ColumnInfo(name = "extra_fees") val extraFees: Float,
    @ColumnInfo(name = "vat") val vat: Float,
    @ColumnInfo(name = "deductions") val deductions: Float,
    @ColumnInfo(name = "total_payable") val totalPayable: Float,
    @ColumnInfo(name = "updated") val updated: Long
)
