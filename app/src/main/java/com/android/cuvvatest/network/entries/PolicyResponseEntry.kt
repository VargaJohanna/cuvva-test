package com.android.cuvvatest.network.entries

data class PolicyResponseEntry(
    val type: String,
    val timestamp: String,
    val uniqueKey: String,
    val payload: PayloadEntry
)
