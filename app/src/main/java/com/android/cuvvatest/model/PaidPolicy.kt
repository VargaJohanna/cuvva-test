package com.android.cuvvatest.model

data class PaidPolicy(
    private val policyId: String,
    private val timestamp: String,
    private val uniqueKey: String,
    private val underwriterPremium: Float,
    private val commission: Float,
    private val totalPremium: Float,
    private val ipt: Float,
    private val iptRate: Float,
    private val extraFees: Float,
    private val vat: Float,
    private val deductions: Float,
    private val totalPayable: Float
)