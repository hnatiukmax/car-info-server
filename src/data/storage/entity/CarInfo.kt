package com.sectumsempra.data.storage.entity

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Column
import java.io.Serializable

internal data class CarInfo(
    val id: Int,
    val digits: String,
    val vendor: String,
    val model: String
) : Serializable

object CarsInfo : IntIdTable() {
    val digits: Column<String> = varchar("digits", 255)
    val vendor: Column<String> = varchar("vendor", 255)
    val model: Column<String> = varchar("model", 255)
}