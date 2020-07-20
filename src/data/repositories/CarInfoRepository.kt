package com.sectumsempra.data.repositories

import com.sectumsempra.data.network.client.RestClient
import com.sectumsempra.data.network.client.carInfoService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CarInfoRepository {

    private val carInfoService = RestClient().carInfoService

    suspend fun getCarInfo(digits: String) = withContext(Dispatchers.IO) {
        return@withContext carInfoService.getCarInfo(digits)
    }
}