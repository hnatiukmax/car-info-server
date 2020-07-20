package com.sectumsempra.server

import java.lang.IllegalArgumentException

internal const val DATABASE_GAI_URL = "DATABASE_GAI_URL"
internal const val IS_TESTING_MODE = "IS_TESTING_MODE"

internal fun String.fromEnv() = System.getenv(this)
    ?: throw IllegalArgumentException("$this env is missed")

internal fun String.fromEnvOrNull() = System.getenv(this)
