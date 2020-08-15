package com.sectumsempra.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.sectumsempra.data.storage.entity.User
import com.sectumsempra.server.JWT_KEY
import com.sectumsempra.server.fromEnv
import org.joda.time.DateTime
import org.joda.time.Hours

class JwtService {

    companion object {
        private const val EXPIRES_AT_HOURS = 24
        private const val issuer = "car-info"
        private const val JWT_SUBJECT = "Authentication"
    }

    private val jwtSecretKey
        get() = JWT_KEY.fromEnv()

    private val algorithm
        get() = Algorithm.HMAC512(jwtSecretKey)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(user: User): String = JWT.create()
        .withSubject(JWT_SUBJECT)
        .withIssuer(issuer)
        .withClaim("id", user.userId)
        .withExpiresAt(expiresAt())
        .sign(algorithm)

    private fun expiresAt() = (DateTime.now() + Hours.ONE.multipliedBy(EXPIRES_AT_HOURS)).toDate()
}