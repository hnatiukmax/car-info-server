package com.sectumsempra.api.routing.authentication

import com.sectumsempra.api.PUB_API_VERSION
import com.sectumsempra.api.USER_AlREADY_EXIST
import com.sectumsempra.api.asErrorResponse
import com.sectumsempra.api.redirect
import com.sectumsempra.api.request.authentication.SocialAuthRequest
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

const val SOCIAL_AUTH_ENDPOINT = "$PUB_API_VERSION/auth"

@KtorExperimentalLocationsAPI
@Location(SOCIAL_AUTH_ENDPOINT)
class SocialAuthentication {
    @Location("/social-login")
    class Login(val parent: SocialAuthentication = SocialAuthentication())

    @Location("/social-register")
    class Register(val parent: SocialAuthentication = SocialAuthentication())
}

@KtorExperimentalLocationsAPI
fun Route.socialAuthentication(authRepository: AuthRepository, jwtService: JwtService, passwordHash: PasswordHash) {

    post<SocialAuthentication.Login> {
        val socialAuthRequest = call.receive<SocialAuthRequest>()
        val authUserInfo = authRepository.getAuthInfo(socialAuthRequest)
        val potentialUser = authRepository.getUserByCredential(authUserInfo.name, passwordHash.hash(authUserInfo.id))

        if (potentialUser != null) {
            call.respond(HttpStatusCode.OK, AuthResponse(potentialUser.name, jwtService.generateToken(potentialUser)))
        } else {
            call.redirect(SocialAuthentication.Register())
        }
    }

    post<SocialAuthentication.Register> {
        val socialAuthRequest = call.receive<SocialAuthRequest>()
        val authUserInfo = authRepository.getAuthInfo(socialAuthRequest)
        val potentialUser = authRepository.getUserByCredential(authUserInfo.name, passwordHash.hash(authUserInfo.id))

        if (potentialUser != null) {
            call.respond(HttpStatusCode.BadRequest, USER_AlREADY_EXIST.asErrorResponse)
            return@post
        }

        val newUser = User(
            name = authUserInfo.name,
            createdBy = socialAuthRequest.provider,
            login = authUserInfo.name,
            hashedPassword = passwordHash.hash(authUserInfo.id)
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