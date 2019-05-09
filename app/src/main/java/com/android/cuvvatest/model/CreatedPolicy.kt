package com.android.cuvvatest.model

import org.threeten.bp.LocalDateTime

data class CreatedPolicy(
    val policyId: String,
    val timestamp: String,
    val uniqueKey: String,
    val userId: String,
    val originalPolicyId: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val updated: LocalDateTime,
    val extensionPolicy: Boolean,
    val active: Boolean
)
