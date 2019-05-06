package com.android.cuvvatest.model

data class CancelledPolicy(
    private val policyId: String,
    private val timestamp: String,
    private val uniqueKey: String,
    private val cancelType: String
)