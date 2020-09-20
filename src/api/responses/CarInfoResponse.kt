package com.sectumsempra.api.responses

import com.google.gson.annotations.SerializedName

data class CarInfoResponse(
    @SerializedName("digits")
    val digits: String,
    @SerializedName("vendor")
    val vendor: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("color")
    val color: String,
    @SerializedName("fuel")
    val fuel: String,
    @SerializedName("engine_size")
    val engineSize: String,
    @SerializedName("stolen")
    val isStolen: Boolean,
    @SerializedName("photoUrl")
    val photoUrl: String,
    @SerializedName("operations")
    val operations: List<OperationResponse>
) {

    data class OperationResponse(
        @SerializedName("isLast")
        val isLast: Boolean,
        @SerializedName("regAt")
        val regAt: String,
        @SerializedName("operation")
        val operation: String,
        @SerializedName("address")
        val address: String
    )
}