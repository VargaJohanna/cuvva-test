package com.android.cuvvatest.repositories.policies.paid

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.cuvvatest.model.PaidPolicy

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

fun PaidPolicyEntity.toPaidPolicy() = PaidPolicy(
    policyId = policyId,
    timestamp = timestamp,
    uniqueKey = uniqueKey,
    underwriterPremium = underwriterPremium,
    commission = commission,
    totalPremium = totalPremium,
    ipt = ipt,
    iptRate = iptRate,
    extraFees = extraFees,
    vat = vat,
    deductions = deductions,
    totalPayable = totalPayable
)
