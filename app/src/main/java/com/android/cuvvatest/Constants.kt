package com.android.cuvvatest

import org.threeten.bp.LocalDateTime

object Constants {
    const val CUVVA_POLICY_BASE_URL = "http://www.mocky.io/"
    //Set the current date so there are active policies. It should be LocalDateTime.now()
    val CURRENT_DATE: LocalDateTime = LocalDateTime.of(2019, 4, 18, 11, 0)

    object TypeValues {
        const val CREATED = "policy_created"
        const val PAID = "policy_financial_transaction"
        const val CANCELLED = "policy_cancelled"
    }
}