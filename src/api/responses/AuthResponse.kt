package com.sectumsempra.api.responses

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("token")
    val token: String
)