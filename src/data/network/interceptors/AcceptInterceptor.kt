package com.sectumsempra.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

internal class AcceptInterceptor : Interceptor {

    companion object {
        private const val ACCEPT_HEADER_NAME = "Accept"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(ACCEPT_HEADER_NAME, "application/json")
            .build()

        return chain.proceed(request)
    }
}