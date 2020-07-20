package com.sectumsempra.data.network.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CarInfo(
    @Json(name = "digits")
    val digits: String,
    @Json(name = "vendor")
    val vendor: String,
    @Json(name = "model")
    val model: String
)