package com.sectumsempra.api.routing.authentication

import com.sectumsempra.api.PUB_API_VERSION
import com.sectumsempra.api.USER_AlREADY_EXIST
import com.sectumsempra.api.USER_NOT_FOUND
import com.sectumsempra.api.asErrorResponse
import com.sectumsempra.api.request.authentication.AuthProvider
import com.sectumsempra.api.request.authentication.EmailLoginRequest
import com.sectumsempra.api.request.authentication.EmailRegisterRequest
import com.sectumsempra.api.responses.AuthResponse
import com.sectumsempra.auth.JwtService
import com.sectumsempra.auth.password.PasswordHash
import com.sectumsempra.data.repositories.AuthRepository
import com.sectumsempra.data.storage.entity.User
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route

const val AUTH_ENDPOINT = "$PUB_API_VERSION/auth"

@KtorExperimentalLocationsAPI
@Location(AUTH_ENDPOINT)
class Authentication {
    @Location("/login")
    class Login(val parent: Authentication = Authentication())

    @Location("/register")
    class Register(val parent: Authentication = Authentication())
}

@KtorExperimentalLocationsAPI
fun Route.emailAuthentication(authRepository: AuthRepository, jwtService: JwtService, passwordHash: PasswordHash) {

    post<Authentication.Login> {
        val loginRequestBody = call.receive<EmailLoginRequest>()
        val potentialUser = authRepository.getUserByCredential(loginRequestBody.email, passwordHash.hash(loginRequestBody.password))

        if (potentialUser != null) {
            call.respond(HttpStatusCode.OK, AuthResponse(potentialUser.name, jwtService.generateToken(potentialUser)))
        } else {
            call.respond(HttpStatusCode.NotFound, USER_NOT_FOUND.asErrorResponse)
        }
    }

    post<Authentication.Register> {
        val registerRequestBody = call.receive<EmailRegisterRequest>()
        val potentialUser = authRepository.getUserByCredential(registerRequestBody.email, passwordHash.hash(registerRequestBody.password))

        if (potentialUser != null) {
            call.respond(HttpStatusCode.BadRequest, USER_AlREADY_EXIST.asErrorResponse)
            return@post
        }

        val newUser = User(
            name = registerRequestBody.name,
            createdBy = AuthProvider.EMAIL,
            login = registerRequestBody.email,
            hashedPassword = passwordHash.hash(registerRequestBody.password)
        )

        try {
            authRepository.createUser(newUser)
            val token = jwtService.generateToken(newUser)
            call.respond(HttpStatusCode.OK, AuthResponse(newUser.name, token))
        } catch (ex: Exception) {
            call.respond(HttpStatusCode.InternalServerError, ex.localizedMessage.asErrorResponse)
        }
    }
}