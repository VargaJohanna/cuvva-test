package com.android.cuvvatest.network.entities

import com.google.gson.annotations.SerializedName

sealed class PayloadEntity (
) {
    data class PayloadCreated(
        @SerializedName("type")
        private val type: String,
        @SerializedName("timestamp")
        private val timestamp: String,
        @SerializedName("unique_key")
        private val uniqueKey: String,
        @SerializedName("user_id")
        private val userId: String,
        @SerializedName("policy_id")
        private val policyId: String,
        @SerializedName("original_policy_id")
        private val originalPolicyId: String,
        @SerializedName("start_date")
        private val startDate: String,
        @SerializedName("end_date")
        private val endDate: String,
        @SerializedName("vehicle")
        private val vehicle: VehicleEntity
    ) : PayloadEntity()

    data class PayloadPaid(
        @SerializedName("type")
        private val type: String,
        @SerializedName("timestamp")
        private val timestamp: String,
        @SerializedName("unique_key")
        private val uniqueKey: String,
        @SerializedName("policy_id")
        val policyId: String,
        @SerializedName("pricing")
        val pricing: PricingEntity
    ) : PayloadEntity()

    data class PayloadCancelled(
        @SerializedName("type")
        private val type: String,
        @SerializedName("timestamp")
        private val timestamp: String,
        @SerializedName("unique_key")
        private val uniqueKey: String,
        @SerializedName("policy_id")
        val policyId: String,
        @SerializedName("type")
        val cancelType: String
    ) : PayloadEntity()

    object UnknownPayload: PayloadEntity()
}