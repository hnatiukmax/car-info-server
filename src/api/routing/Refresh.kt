package com.sectumsempra.api.routing

import com.sectumsempra.api.asDataResponse
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route
import org.joda.time.DateTime

const val REFRESH_ENDPOINT = "/refresh"

@KtorExperimentalLocationsAPI
@Location(REFRESH_ENDPOINT)
class Refresh

@KtorExperimentalLocationsAPI
fun Route.refresh() {
    get<Refresh> {
        call.respond(HttpStatusCode.OK, "Refresh. Time - ${DateTime.now().toDate()}".asDataResponse)
    }
}