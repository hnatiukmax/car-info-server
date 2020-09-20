package com.sectumsempra.data.network.entity.car

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegionNT(
    @Json(name = "name_ua")
    val name: String,
    @Json(name = "old_code")
    val oldCode: String,
    @Json(name = "new_code")
    val newCode: String
)