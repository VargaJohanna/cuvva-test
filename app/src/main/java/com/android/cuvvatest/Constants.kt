package com.android.cuvvatest

import org.threeten.bp.LocalDateTime

object Constants {
    /**
     * For testing purposes the current date is a hard coded date in the past. Like this there are always active policies.
     * It should be LocalDateTime.now()
     */
    val CURRENT_DATE: LocalDateTime = LocalDateTime.of(2019, 4, 18, 11, 0)

    const val CUVVA_POLICY_BASE_URL = "http://www.mocky.io/"

    object TypeValues {
        const val CREATED = "policy_created"
        const val PAID = "policy_financial_transaction"
        const val CANCELLED = "policy_cancelled"
    }

    val FROM_LOCAL_DATE_TIME: LocalDateTime = LocalDateTime.from(CURRENT_DATE)
}