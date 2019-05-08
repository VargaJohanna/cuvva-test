package com.android.cuvvatest.model

import org.threeten.bp.LocalDateTime

data class HomeDataObject (
    val vehicle: Vehicle,
    val createdPolicyList: List<CreatedPolicy>,
    val updated: LocalDateTime
)