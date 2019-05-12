package com.android.cuvvatest.model

data class PaidPolicy(
    val policyId: String,
    val timestamp: String,
    val uniqueKey: String,
    val underwriterPremium: Float,
    val commission: Float,
    val totalPremium: Float,
    val ipt: Float,
    val iptRate: Float,
    val extraFees: Float,
    val vat: Float,
    val deductions: Float,
    val totalPayable: Float
)