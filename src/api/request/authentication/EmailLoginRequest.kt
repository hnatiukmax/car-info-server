package com.sectumsempra.api.request.authentication

import com.google.gson.annotations.SerializedName

data class EmailLoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)