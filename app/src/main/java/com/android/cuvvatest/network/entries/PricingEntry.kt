package com.android.cuvvatest.network.entries

data class PricingEntry(
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
