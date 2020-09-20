package com.sectumsempra.domain.entity.car

data class Number(
    val countryRegion: String,
    val digitNumber: String,
    val series: String
) {

    val fullNumber get() = "$countryRegion$digitNumber$series"
}

