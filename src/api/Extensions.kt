package com.sectumsempra.api

import com.sectumsempra.api.responses.ErrorResponse
import io.ktor.application.ApplicationCall
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.locations
import io.ktor.response.respondRedirect

val String.asErrorResponse get() = ErrorResponse(this)

@KtorExperimentalLocationsAPI
suspend fun ApplicationCall.redirect(location: Any) {
    respondRedirect(application.locations.href(location))
}