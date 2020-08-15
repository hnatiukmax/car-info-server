package com.sectumsempra.api.request.authentication

import com.google.gson.annotations.SerializedName

data class EmailRegisterRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)