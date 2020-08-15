package com.sectumsempra.api.request.authentication

import com.google.gson.annotations.SerializedName

data class SocialAuthRequest(
    @SerializedName("provider")
    val provider: AuthProvider,
    @SerializedName("token")
    val token: String
)