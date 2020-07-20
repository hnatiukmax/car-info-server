package com.sectumsempra.data.network.client

import com.sectumsempra.data.network.services.CarInfoService

internal val RestClient.carInfoService
    get() = retrofit.create(CarInfoService::class.java)