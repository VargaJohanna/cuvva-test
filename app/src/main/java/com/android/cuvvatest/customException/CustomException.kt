package com.android.cuvvatest.customException

data class CustomException(
    val errorMessage: String
) : Exception()