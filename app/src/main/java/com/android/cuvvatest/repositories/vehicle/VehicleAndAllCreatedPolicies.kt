package com.android.cuvvatest.repositories.vehicle

import androidx.room.Relation
import com.android.cuvvatest.repositories.policies.created.CreatedPolicyEntity

data class VehicleAndAllCreatedPolicies(
    var vrm: String,
    @Relation(parentColumn = "vrm", entityColumn = "vrm")
    var vehicleEntity: List<VehicleEntity>,
    @Relation(parentColumn = "vrm", entityColumn = "vrm")
    var createdPolicyList: List<CreatedPolicyEntity>
) {
    constructor() : this("", emptyList(), emptyList())
}