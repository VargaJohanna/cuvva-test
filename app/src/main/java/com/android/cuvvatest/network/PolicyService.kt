package com.android.cuvvatest.network

import com.android.cuvvatest.network.entities.PolicyResponseList
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface PolicyService {

    @GET("v2/5ccf4e91300000770752c4db/")
    fun getPolicies(): Single<Response<PolicyResponseList>>
}