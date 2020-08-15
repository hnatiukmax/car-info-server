package com.sectumsempra.data.network.entity.authentication

import com.sectumsempra.data.network.mapping.utils.JsonDataWrapper
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FacebookAuthInfo(
    @Json(name = "id")
    val accountId: String,
    @Json(name = "name")
    val fullName: String,
    @Json(name = "picture")
    val photoInfo: JsonDataWrapper<FacebookPhotoInfo>
) : SocialAuthInfo(accountId, fullName, photoInfo.data.photoUrl) {

    @JsonClass(generateAdapter = true)
    data class FacebookPhotoInfo(
        @Json(name = "height")
        val height: Int,
        @Json(name = "width")
        val width: Int,
        @Json(name = "url")
        val photoUrl: String?
    )
}