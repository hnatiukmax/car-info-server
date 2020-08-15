package com.sectumsempra.server

internal const val DATABASE_GAI_URL = "DATABASE_GAI_URL"
internal const val IS_TESTING_MODE = "IS_TESTING_MODE"
internal const val DATABASE_URl = "DATABASE_URl"
internal const val JWT_KEY = "JWT_KEY"
internal const val SECRET_KEY = "SECRET_KEY"

internal fun String.fromEnv() = System.getenv(this)
    ?: throw IllegalArgumentException("$this environment variable is missed")

internal fun String.fromEnvOrNull() = System.getenv(this)