package com.sectumsempra.server

internal fun isTestingMode() = System.getenv(IS_TESTING_MODE)?.toBoolean() ?: false