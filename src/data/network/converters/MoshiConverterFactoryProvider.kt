package com.sectumsempra.data.network.converters

import com.squareup.moshi.Moshi
import retrofit2.Converter
import retrofit2.converter.moshi.MoshiConverterFactory

internal class MoshiConverterFactoryProvider : ConverterFactoryProvider{

    override fun getConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create(getMoshi())
    }

    private fun getMoshi() : Moshi {
        return Moshi.Builder().build()
    }
}