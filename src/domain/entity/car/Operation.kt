package com.sectumsempra.domain.entity.car

data class Operation(
    val isLast: Boolean,
    val regAt: String,
    val notes: String,
    val operation: String,
    val address: String
)