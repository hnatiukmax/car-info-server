package com.sectumsempra.data.storage.database

import com.sectumsempra.data.storage.entity.CarsInfo
import com.sectumsempra.data.storage.entity.Users
import com.sectumsempra.server.DATABASE_URl
import com.sectumsempra.server.fromEnv
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

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
        val config = HikariConfig()

        config.apply {
            driverClassName = DRIVER_CLASS_NAME
            jdbcUrl = DATABASE_URl.fromEnv()
            transactionIsolation = TRANSACTION_ISOLATION
            maximumPoolSize = 3
            password = "admin"
            isAutoCommit = false
        }.validate()

        return HikariDataSource(config)
    }
}

suspend fun <T> dbQuery(block: () -> T) = withContext(Dispatchers.IO) {
    transaction {
        block()
    }
}