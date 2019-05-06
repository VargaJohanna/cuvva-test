package com.android.cuvvatest.network

import com.android.cuvvatest.network.entities.*
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class PolicyDeserializer : JsonDeserializer<PolicyResponseList> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PolicyResponseList {
        return if (json != null) {
            val jsonArray = json.asJsonArray
            val listToReturn = mutableListOf<PolicyResponseEntity>()
            for (jsonElement in jsonArray) {
                val jsonObject = jsonElement.asJsonObject
                val type = jsonObject.get("type").asString
                val timestamp = jsonObject.get("timestamp").asString
                val uniqueKey = jsonObject.get("unique_key").asString
                val payload = jsonObject.get("payload").asJsonObject
                val payloadEntity = getPayloadEntity(type, jsonObject, payload)

                listToReturn.add(
                    PolicyResponseEntity(
                        type = type,
                        timestamp = timestamp,
                        uniqueKey = uniqueKey,
                        payload = payloadEntity
                    )
                )
            }
            PolicyResponseList(listToReturn)
        } else {
            PolicyResponseList(emptyList())
        }
    }

    private fun getPayloadEntity(type: String, jsonObject: JsonObject, payload: JsonObject): PayloadEntity {
        return when (type) {
            TypeValues.CREATED -> {
                val vehicle = payload.get("vehicle").asJsonObject
                PayloadEntity.PayloadCreated(
                    type = type,
                    timestamp = jsonObject.get("timestamp").asString,
                    uniqueKey = jsonObject.get("unique_key").asString,
                    userId = payload.get("user_id").asString,
                    policyId = payload.get("policy_id").asString,
                    originalPolicyId = payload.get("original_policy_id").asString,
                    startDate = payload.get("start_date").asString,
                    endDate = payload.get("end_date").asString,
                    vehicle = VehicleEntity(
                        vrm = vehicle.get("vrm").asString,
                        prettyVrm = vehicle.get("prettyVrm").asString,
                        make = vehicle.get("make").asString,
                        model = vehicle.get("model").asString,
                        color = vehicle.get("color").asString
                    )
                )
            }
            TypeValues.PAID -> {
                val pricing = payload.get("pricing").asJsonObject
                PayloadEntity.PayloadPaid(
                    type = type,
                    timestamp = jsonObject.get("timestamp").asString,
                    uniqueKey = jsonObject.get("unique_key").asString,
                    policyId = payload.get("policy_id").asString,
                    pricing = PricingEntity(
                        underwriterPremium = pricing.get("underwriter_premium").asFloat,
                        commission = pricing.get("commission").asFloat,
                        totalPremium = pricing.get("total_premium").asFloat,
                        ipt = pricing.get("ipt").asFloat,
                        iptRate = pricing.get("ipt_rate").asFloat,
                        extraFees = pricing.get("extra_fees").asFloat,
                        vat = pricing.get("vat").asFloat,
                        deductions = pricing.get("deductions").asFloat,
                        totalPayable = pricing.get("total_payable").asFloat
                    )
                )
            }
            TypeValues.CANCELLED -> PayloadEntity.PayloadCancelled(
                type = type,
                timestamp = jsonObject.get("timestamp").asString,
                uniqueKey = jsonObject.get("unique_key").asString,
                policyId = payload.get("policy_id").asString,
                cancelType = payload.get("type").asString
            )
            else -> PayloadEntity.UnknownPayload
        }
    }

    object TypeValues {
        const val CREATED = "policy_created"
        const val PAID = "policy_financial_transaction"
        const val CANCELLED = "policy_cancelled"
    }
}