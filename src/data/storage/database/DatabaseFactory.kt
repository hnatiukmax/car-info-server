package com.sectumsempra.data.storage.database

import com.sectumsempra.api.TRANSACTION_ISOLATION
import com.sectumsempra.data.storage.entity.CarsInfo
import com.sectumsempra.data.storage.entity.Users
import com.sectumsempra.server.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.net.URI

object DatabaseFactory {

    fun init() {
        Database.connect(hikari())

        transaction {
            SchemaUtils.create(CarsInfo, Users)

            CarsInfo.insert {
                it[digits] = "AC1111BO"
                it[vendor] = "Audi"
                it[model] = "A4"
            }
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = DATABASE_DRIVER.fromEnv()
            jdbcUrl = if (isTestingMode()) TEST_DATABASE_URL.fromEnv() else buildJDBCUrl()
            transactionIsolation = TRANSACTION_ISOLATION
            maximumPoolSize = 3
            isAutoCommit = false
        }.also {
            it.validate()
        }

        return HikariDataSource(config)
    }

    private fun buildJDBCUrl(): String {
        val databaseJDBCUrl = DATABASE_JDBC_URL.fromEnv()
        val databaseUrl = URI(DATABASE_URL.fromEnv())
        val username = databaseUrl.userInfo.split(":").toTypedArray()[0]
        val password = databaseUrl.userInfo.split(":").toTypedArray()[1]

        return StringBuilder()
            .append("$databaseJDBCUrl://")
            .append("${databaseUrl.host}:")
            .append("${databaseUrl.port}")
            .append("${databaseUrl.path}?")
            .append("sslmode=require&")
            .append("user=$username&")
            .append("password=$password")
            .toString()
    }
}

suspend fun <T> dbQuery(block: () -> T) = withContext(Dispatchers.IO) {
    transaction {
        block()
    }
}