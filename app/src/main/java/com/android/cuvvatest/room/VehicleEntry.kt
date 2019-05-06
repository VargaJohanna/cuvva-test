package com.android.cuvvatest.room

data class VehicleEntry (
    private val vrm: String,
    private val prettyVrm: String,
    val make: String,
    val model: String,
    val color: String,
    val policies: List<PolicyEntry>
)