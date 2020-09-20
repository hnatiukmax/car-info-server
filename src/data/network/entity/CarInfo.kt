package com.sectumsempra.data.network.entity

import com.sectumsempra.api.responses.CarInfoResponse
import com.sectumsempra.api.withoutWhitespace
import com.sectumsempra.data.network.entity.car.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CarInfo(
    @Json(name = "digits")
    val digits: String,
    @Json(name = "vendor")
    val vendor: String,
    @Json(name = "model")
    val model: String,
    @Json(name = "region")
    val region: RegionNT,
    @Json(name = "year")
    val year: Int,
    @Json(name = "stolen")
    val isStolen: Boolean,
    @Json(name = "photoUrl")
    val photoUrl: String,
    @Json(name = "operations")
    val operations: List<OperationNT>
)

val CarInfo.asResponseEntity get() = CarInfoResponse(
    vendor = vendor,
    model = model,
    digits = digits,
    region = region.name,
    year = year,
    color = operations.last().color.withoutWhitespace,
    fuel = operations.last().fuel?.replaceFirst("\\s".toRegex(), "") ?: "",
    engineSize = operations.last().engineSize?.withoutWhitespace ?: "",
    isStolen = isStolen,
    photoUrl = photoUrl,
    operations = operations.map { it.asResponseEntity }
)