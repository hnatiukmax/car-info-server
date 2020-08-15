package com.sectumsempra.data.network.client

import com.sectumsempra.data.network.services.AuthenticationService
import com.sectumsempra.data.network.services.CarInfoService

internal val RestClient.carInfoService
    get() = retrofit.create(CarInfoService::class.java)

internal val RestClient.authService
    get() = retrofit.create(AuthenticationService::class.java)