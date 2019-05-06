package com.android.cuvvatest.network.entities

import com.google.gson.annotations.SerializedName

data class PricingEntity(
    @SerializedName("underwriter_premium")
    val underwriterPremium: Float,
    @SerializedName("commission")
    val commission: Float,
    @SerializedName("total_premium")
    val totalPremium: Float,
    @SerializedName("ipt")
    val ipt: Float,
    @SerializedName("ipt_rate")
    val iptRate: Float,
    @SerializedName("extra_fees")
    val extraFees: Float,
    @SerializedName("vat")
    val vat: Float,
    @SerializedName("deductions")
    val deductions: Float,
    @SerializedName("total_payable")
    val totalPayable: Float
)
