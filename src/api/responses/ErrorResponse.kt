package com.sectumsempra.api.responses

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val message: String
)