package com.sectumsempra.api.routing

import com.sectumsempra.api.API_VERSION
import com.sectumsempra.api.asErrorResponse
import com.sectumsempra.data.repositories.CarInfoRepository
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route

const val CAR_INFO_ENDPOINT = "$API_VERSION/car-info"

@KtorExperimentalLocationsAPI
@Location("$CAR_INFO_ENDPOINT/{number}")
class CarInfo(val number: String)

@KtorExperimentalLocationsAPI
fun Route.carInfo(carInfoRepository: CarInfoRepository) {

    get<CarInfo> {
        try {
            val carInfo = carInfoRepository.getCarInfo(it.number)
            call.respond(HttpStatusCode.OK, carInfo)
        } catch (ex: Exception) {
            call.respond(HttpStatusCode.BadRequest, ex.localizedMessage.asErrorResponse)
        }
    }
}