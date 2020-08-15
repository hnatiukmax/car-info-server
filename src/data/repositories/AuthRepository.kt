package com.sectumsempra.data.repositories

import com.sectumsempra.api.request.authentication.AuthProvider
import com.sectumsempra.api.request.authentication.SocialAuthRequest
import com.sectumsempra.data.network.client.RestClient
import com.sectumsempra.data.network.client.authService
import com.sectumsempra.data.storage.database.dbQuery
import com.sectumsempra.data.storage.entity.User
import com.sectumsempra.data.storage.entity.Users
import com.sectumsempra.data.storage.entity.asUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime

class AuthRepository {

    private val authService = RestClient().authService

    suspend fun getAuthInfo(socialAuthRequest: SocialAuthRequest) = withContext(Dispatchers.IO) {
        return@withContext when(socialAuthRequest.provider) {
            AuthProvider.FACEBOOK -> authService.getFacebookUserAuthInfo(socialAuthRequest.token)
            AuthProvider.GOOGLE -> authService.getGoogleUserAuthInfo(socialAuthRequest.token)
            AuthProvider.EMAIL -> throw IllegalArgumentException("${socialAuthRequest.provider} is not supported in social auth.")
        }
    }

    suspend fun createUser(user: User) = dbQuery {
        Users.insert {
            it[name] = user.name
            it[photoUrl] = user.photoUrl.orEmpty()
            it[createdAt] = DateTime.now()
            it[createdBy] = user.createdBy
            it[login] = user.login
            it[hashedPassword] = user.hashedPassword
        }
        Unit
    }

    suspend fun getUserById(userId: Int) = dbQuery {
        Users.select { Users.id.eq(userId) }
            .map { it.asUser }
            .singleOrNull()
    }

    suspend fun getUserByCredential(login: String, hashedPassword: String) = dbQuery {
        Users.select { Users.login.eq(login) and Users.hashedPassword.eq(hashedPassword) }
            .map { it.asUser }
            .singleOrNull()
    }
}