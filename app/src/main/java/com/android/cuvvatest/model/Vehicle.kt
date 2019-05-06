package com.android.cuvvatest.model

import java.util.*

data class Vehicle(
    val vrm: String,
//    val prettyVrm: String,
//    val make: String,
//    val model: String,
//    val color: String,
//    val updated: Date,
    val policyIds: List<String>
//    ,
//    val createdPolicyList: List<CreatedPolicy>,
//    val paidPolicyList: List<PaidPolicy>,
//    val cancelledPolicyList: List<CancelledPolicy>
)