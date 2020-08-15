package com.sectumsempra.data.storage.entity

import com.sectumsempra.api.request.authentication.AuthProvider
import io.ktor.auth.Principal
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.joda.time.DateTime
import java.io.Serializable

data class User(
    val userId: String = "",
    val name: String,
    val photoUrl: String? = null,
    val createdAt: DateTime = DateTime.now(),
    val createdBy: AuthProvider,
    val login: String,
    val hashedPassword: String
) : Serializable, Principal

object Users : IntIdTable() {
    val name = varchar("name", 255)
    val photoUrl = varchar("photoUrl", 255)
    val createdAt = datetime("createdAt")
    val createdBy = enumerationByName("createdBy", 255, AuthProvider::class)
    val login = varchar("login", 255)
    val hashedPassword = varchar("password", 255)
}

val ResultRow.asUser: User
    get() = User(
        userId = this[Users.id].toString(),
        name = this[Users.name],
        photoUrl = this[Users.photoUrl],
        createdAt = this[Users.createdAt],
        createdBy = this[Users.createdBy],
        login = this[Users.login],
        hashedPassword = this[Users.hashedPassword]
    )