package com.sectumsempra.data.network.entity.car

import com.sectumsempra.api.responses.CarInfoResponse.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OperationNT(
    @Json(name = "isLast")
    val isLast: Boolean,
    @Json(name = "regAt")
    val regAt: String,
    @Json(name = "model")
    val model: String,
    @Json(name = "vendor")
    val vendor: String,
    @Json(name = "modelYear")
    val modelYear: Int,
    @Json(name = "notes")
    val notes: String,
    @Json(name = "operation")
    val operation: String,
    @Json(name = "address")
    val address: String
)

val OperationNT.asResponseEntity get() = OperationResponse(
    isLast = isLast,
    regAt = regAt,
    operation = operation,
    address = address
)

val OperationNT.color get() = notes.split(",")[0]

val OperationNT.fuel get() = notes.split(",")[1].takeIf { !it.contains("^[1-9].[1-9]L".toRegex()) }

val OperationNT.engineSize get() = notes.split(",").getOrNull(2)