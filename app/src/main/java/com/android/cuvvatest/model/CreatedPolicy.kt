package com.android.cuvvatest.model

import org.threeten.bp.LocalDateTime

data class CreatedPolicy(
    private val policyId: String,
    private val timestamp: String,
    private val uniqueKey: String,
    private val userId: String,
    private val originalPolicyId: String,
    private val startDate: LocalDateTime,
    private val endDate: LocalDateTime,
    private val updated: LocalDateTime,
    private val extensionPolicy: Boolean
)
