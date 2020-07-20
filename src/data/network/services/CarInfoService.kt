package com.sectumsempra.data.network.services

import com.sectumsempra.data.network.entity.CarInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface CarInfoService {

    @GET("/nomer/{digits}")
    suspend fun getCarInfo(@Path("digits") digits: String): CarInfo
}