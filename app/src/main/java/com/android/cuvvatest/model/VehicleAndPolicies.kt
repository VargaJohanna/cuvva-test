package com.android.cuvvatest.model

import org.threeten.bp.LocalDateTime

data class VehicleAndPolicies(
    val vehicle: Vehicle,
    val createdPolicyList: List<CreatedPolicy>,
    val updated: LocalDateTime,
    val hasActive: Boolean,
    val remainingTimeOfActive: Long
)