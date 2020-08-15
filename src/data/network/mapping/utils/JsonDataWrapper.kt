package com.sectumsempra.data.network.mapping.utils

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JsonDataWrapper<T>(
    @Json(name = "data")
    val data: T
)