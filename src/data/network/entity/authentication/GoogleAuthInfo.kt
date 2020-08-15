package com.sectumsempra.data.network.entity.authentication

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GoogleAuthInfo(
    @Json(name = "kid")
    val kid: String,
    @Json(name = "name")
    val fullName: String,
    @Json(name = "given_name")
    val firstName: String,
    @Json(name = "family_name")
    val secondName: String,
    @Json(name = "picture")
    val photo: String?
) : SocialAuthInfo(kid, fullName, photo)