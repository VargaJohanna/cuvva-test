package com.android.cuvvatest.network.entities

import com.google.gson.annotations.SerializedName

data class VehicleEntity(
    @SerializedName("vrm")
    val vrm: String,
    @SerializedName("prettyVrm")
    val prettyVrm: String,
    @SerializedName("make")
    val make: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("color")
    val color: String
)
