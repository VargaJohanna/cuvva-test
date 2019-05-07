package com.android.cuvvatest.model

import org.threeten.bp.LocalDate

data class CancelledPolicy(
    private val policyId: String,
    private val timestamp: String,
    private val uniqueKey: String,
    private val cancelType: String,
    private val updated: LocalDate
    )