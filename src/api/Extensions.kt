package com.sectumsempra.api

import com.sectumsempra.api.responses.ErrorResponse
import com.sectumsempra.data.network.mapping.utils.JsonDataWrapper
import io.ktor.application.ApplicationCall
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.locations
import io.ktor.response.respondRedirect

val String.asErrorResponse get() = ErrorResponse(this)
val String.asDataResponse get() = JsonDataWrapper(this)

@KtorExperimentalLocationsAPI
suspend fun ApplicationCall.redirect(location: Any) {
    respondRedirect(application.locations.href(location))
}

internal val String.withoutWhitespace get() = this.replace("\\s".toRegex(), "")