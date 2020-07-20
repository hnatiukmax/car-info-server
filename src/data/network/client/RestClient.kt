package com.sectumsempra.data.network.client

import com.sectumsempra.data.network.converters.MoshiConverterFactoryProvider
import com.sectumsempra.server.DATABASE_GAI_URL
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

// Temporary constructor
internal class RestClient(
    private val baseUrl: String = System.getenv(DATABASE_GAI_URL),
    private val okHttpClient: OkHttpClient = OkHttpClientFactory.buildDefaultOkHttpClient(),
    private val converter: Converter.Factory = MoshiConverterFactoryProvider().getConverterFactory()
) {

    internal val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(converter)
        .build()
}