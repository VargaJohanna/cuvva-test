package com.android.cuvvatest.model

import org.threeten.bp.LocalDateTime

data class CancelledPolicy(
    private val policyId: String,
    private val timestamp: String,
    private val uniqueKey: String,
    private val cancelType: String,
    private val updated: LocalDateTime
)