package com.android.cuvvatest.repositories.vehicle

import androidx.room.Relation
import com.android.cuvvatest.repositories.policies.EventEntity

data class VehicleAndAllPolicyIds(
    var vrm: String,
    @Relation(parentColumn = "vrm", entityColumn = "vehicle_vrm")
    var eventEntityList: List<EventEntity>
) {
    constructor() : this ("", emptyList())
}