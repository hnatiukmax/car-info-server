package com.sectumsempra.server

internal const val IS_TESTING_MODE = "IS_TESTING_MODE"

/* Database */
internal const val DATABASE_GAI_URL = "DATABASE_GAI_URL"
internal const val DATABASE_URL = "DATABASE_URL"
internal const val DATABASE_DRIVER = "DATABASE_DRIVER"
internal const val DATABASE_JDBC_URL = "DATABASE_JDBC_URL"
internal const val TEST_DATABASE_URL = "TEST_DATABASE_URL"

/* JWT */
internal const val JWT_KEY = "JWT_KEY"
internal const val SECRET_KEY = "SECRET_KEY"

internal fun String.fromEnv() = System.getenv(this)
    ?: throw IllegalArgumentException("$this environment variable is missed")

internal fun String.fromEnvOrNull() = System.getenv(this)