package com.sectumsempra.auth.password

import com.sectumsempra.server.SECRET_KEY
import com.sectumsempra.server.fromEnv
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.hex
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@KtorExperimentalAPI
class PasswordHashImpl : PasswordHash {

    private val hashKey = hex(SECRET_KEY.fromEnv())
    private val hmacKey = SecretKeySpec(hashKey, HASH_ALGORITHM)

    companion object {
        private const val HASH_ALGORITHM = "HmacSHA1"
    }

    override fun hash(password: String): String {
        val hmac = Mac.getInstance(HASH_ALGORITHM)
        hmac.init(hmacKey)
        return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
    }
}