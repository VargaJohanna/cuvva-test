package com.android.cuvvatest.model

import java.util.*

data class CreatedPolicy(
    private val policyId: String,
    private val active: Boolean,
    private val timestamp: String,
    private val uniqueKey: String,
    private val userId: String,
    private val originalPolicyId: String,
    private val startDate: Date,
    private val endDate: Date,
    private val updated: Date,
    private val extensionPolicy: ExtensionPolicy?
)
