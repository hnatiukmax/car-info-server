package com.sectumsempra.data.network.converters

import retrofit2.Converter

interface ConverterFactoryProvider {

    fun getConverterFactory() : Converter.Factory
}