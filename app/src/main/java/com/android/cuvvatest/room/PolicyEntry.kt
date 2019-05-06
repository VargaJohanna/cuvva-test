package com.android.cuvvatest.room

import java.util.*

data class PolicyEntry(
    val policyId: String,
    val timeStamp: Date,
    val extension: Boolean,
    val active: Boolean,
    val canceled: Boolean,
    val startDate: Date,
    val endDate: Date,
    val durationMinutes: Int,
    val created: Date
    )
