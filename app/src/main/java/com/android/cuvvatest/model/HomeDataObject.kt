package com.android.cuvvatest.model

import org.threeten.bp.LocalDateTime

data class HomeDataObject (
    val vehicle: Vehicle,
    val totalPolicy: Int,
    val hasActive: Boolean,
    val remainingTimeOfActive: LocalDateTime,
    val updated: LocalDateTime
)