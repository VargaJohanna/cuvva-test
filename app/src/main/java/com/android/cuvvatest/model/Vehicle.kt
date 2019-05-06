package com.android.cuvvatest.model

import com.android.cuvvatest.network.entities.PolicyResponseEntity

data class Vehicle(
    val regPlate: String,
    val totalPolicies: Int
)