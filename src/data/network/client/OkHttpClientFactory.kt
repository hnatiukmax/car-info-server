package com.sectumsempra.data.network.client

import com.sectumsempra.data.network.interceptors.AcceptInterceptor
import com.sectumsempra.server.isTestingMode
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.*
import java.util.concurrent.TimeUnit

internal class OkHttpClientFactory private constructor() {

    companion object {
        private const val DEFAULT_CONNECT_TIMEOUT = 20L
        private const val DEFAULT_READ_TIMEOUT = 20L
        private const val DEFAULT_WRITE_TIMEOUT = 20L

        fun buildDefaultOkHttpClient() = OkHttpClientFactory().buildOkHttpClient()
    }

    internal fun buildOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AcceptInterceptor())
            .addInterceptor(getLoggingInterceptor())
            .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    private fun getLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (isTestingMode()) BODY else NONE
    }
}