package com.android.cuvvatest.network.entities

data class PolicyResponseEntity(
    val type: String,
    val timestamp: String,
    val uniqueKey: String,
    val payload: PayloadEntity
)
