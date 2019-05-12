package com.android.cuvvatest.repositories.vehicle

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.cuvvatest.model.Vehicle
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

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

fun VehicleEntity.toVehicle() = Vehicle(
    vrm = vrm,
    prettyVrm = prettyVrm,
    make = make,
    model = model,
    color = color,
    updated = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(updated), ZoneOffset.UTC)
)