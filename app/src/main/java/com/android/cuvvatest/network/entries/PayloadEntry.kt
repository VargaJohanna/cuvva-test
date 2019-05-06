package com.android.cuvvatest.network.entries

sealed class PayloadEntry {
    data class PayloadCreated(
        val type: String,
        val timestamp: String,
        val uniqueKey: String,
        val userId: String,
        val policyId: String,
        val originalPolicyId: String,
        val startDate: String,
        val endDate: String,
        val vehicle: VehicleEntry
    ) : PayloadEntry()

    data class PayloadPaid(
        val type: String,
        val timestamp: String,
        val uniqueKey: String,
        val policyId: String,
        val pricing: PricingEntry
    ) : PayloadEntry()

    data class PayloadCancelled(
        val type: String,
        val timestamp: String,
        val uniqueKey: String,
        val policyId: String,
        val cancelType: String
    ) : PayloadEntry()

    object UnknownPayload : PayloadEntry()
}