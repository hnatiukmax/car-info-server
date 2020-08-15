package com.sectumsempra.api.request.authentication

import com.google.gson.annotations.SerializedName

enum class AuthProvider {
    @SerializedName("facebook")
    FACEBOOK,
    @SerializedName("google")
    GOOGLE,
    @SerializedName("email")
    EMAIL;

    override fun toString(): String {
        return super.toString().toLowerCase()
    }
}