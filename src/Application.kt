package com.sectumsempra

import com.sectumsempra.api.JWT_REALM_NAME
import com.sectumsempra.api.USER_IS_NOT_AUTHORIZED
import com.sectumsempra.api.asErrorResponse
import com.sectumsempra.api.routing.authentication.emailAuthentication
import com.sectumsempra.api.routing.authentication.socialAuthentication
import com.sectumsempra.api.routing.carInfo
import com.sectumsempra.api.routing.refresh
import com.sectumsempra.auth.JwtService
import com.sectumsempra.auth.password.PasswordHashImpl
import com.sectumsempra.data.repositories.AuthRepository
import com.sectumsempra.data.repositories.CarInfoRepository
import com.sectumsempra.data.storage.database.DatabaseFactory
import com.sectumsempra.data.storage.entity.User
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.authentication
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
@KtorExperimentalLocationsAPI
fun Application.module(testing: Boolean = false) {

    DatabaseFactory.init()

    val passwordHash = PasswordHashImpl()
    val jwtService = JwtService()
    val authRepository = AuthRepository()
    val carInfoRepository = CarInfoRepository()

    install(StatusPages) {
        status(HttpStatusCode.Unauthorized) {
            call.respond(HttpStatusCode.OK, USER_IS_NOT_AUTHORIZED.asErrorResponse)
        }
    }

    install(Locations)
    install(DefaultHeaders)
    install(ContentNegotiation) {
        gson()
    }

    install(Authentication) {
        jwt("jwt") {
            verifier(jwtService.verifier)
            realm = JWT_REALM_NAME
            validate {
                val payload = it.payload
                val claimString = payload.getClaim("id").asString()
                val user = authRepository.getUserById(claimString.toInt())
                user
            }
        }
    }

    routing {
        refresh()
        emailAuthentication(authRepository, jwtService, passwordHash)
        socialAuthentication(authRepository, jwtService, passwordHash)
        carInfo(carInfoRepository)
    }
}

val ApplicationCall.userPayload get() = authentication.principal<User>()
