package com.android.cuvvatest.repositories.vehicle

import androidx.room.Relation
import com.android.cuvvatest.Constants
import com.android.cuvvatest.model.VehicleAndPolicies
import com.android.cuvvatest.repositories.policies.created.CreatedPolicyEntity
import com.android.cuvvatest.repositories.policies.created.toCreatedPolicy
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit

data class VehicleAndAllCreatedPolicyEntities(
    var vrm: String,
    @Relation(parentColumn = "vrm", entityColumn = "vrm")
    //The vehicle list will always have only 1 vehicle, but to be able to create Relation, it has to be a list
    var vehicleEntity: List<VehicleEntity>,
    @Relation(parentColumn = "vrm", entityColumn = "vrm")
    var createdPolicyList: List<CreatedPolicyEntity>
) {
    constructor() : this("", emptyList(), emptyList())
}

fun VehicleAndAllCreatedPolicyEntities.toVehicleAndPolicies(): VehicleAndPolicies {
    val createdPolicies = createdPolicyList.map { it.toCreatedPolicy() }

    return VehicleAndPolicies(
        vehicle = vehicleEntity[0].toVehicle(),
        createdPolicyList = createdPolicyList.map { it.toCreatedPolicy() },
        updated = LocalDateTime.now(),
        hasActive = createdPolicyList.map { it.toCreatedPolicy() }.any { it.active },
        // I assume that 1 vehicle can have 1 active policy, so I take the first element in the list where active is true
        remainingTimeOfActive =
        if (createdPolicies.any { it.active })
            LocalDateTime.from(Constants.CURRENT_DATE)
                .until(createdPolicies.first { it.active }.endDate, ChronoUnit.MINUTES)
        else 0
    )
}