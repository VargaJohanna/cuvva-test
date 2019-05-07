package com.android.cuvvatest.repositories.vehicle

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "vehicleTable")
data class VehicleEntity(
    @PrimaryKey
    @ColumnInfo(name = "vrm") val vrm: String,
    @ColumnInfo(name = "pretty_vrm") val prettyVrm: String,
    @ColumnInfo(name = "make") val make: String,
    @ColumnInfo(name = "model") val model: String,
    @ColumnInfo(name = "color") val color: String,
    @ColumnInfo(name = "updated") val updated: Long
)