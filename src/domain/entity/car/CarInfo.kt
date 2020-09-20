package com.sectumsempra.domain.entity.car

data class CarInfo(
    val car: Car,
    val region: Region,
    val operations: List<Operation>
)