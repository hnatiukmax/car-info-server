package com.sectumsempra.domain.entity.car

data class Car(
    val number: Number,
    val model: Model,
    val year: Int,
    val photoUrl: String,
    val isStolen: String
)