package com.sectumsempra.server

internal fun isTestingMode() = IS_TESTING_MODE.fromEnvOrNull()?.toBoolean() ?: false