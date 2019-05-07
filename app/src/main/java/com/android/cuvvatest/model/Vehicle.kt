package com.android.cuvvatest.model

import org.threeten.bp.LocalDateTime

data class Vehicle(
    val vrm: String,
    val prettyVrm: String,
    val make: String,
    val model: String,
    val color: String,
    val updated: LocalDateTime
)