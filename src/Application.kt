package com.sectumsempra

import com.sectumsempra.data.repositories.CarInfoRepository
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    // for test
    routing {
        get("/") {
            val number = "CB1080BC"
            val carInfoRepository = CarInfoRepository()
            val vendor = carInfoRepository.getCarInfo(number)

            call.respond(HttpStatusCode.Accepted, vendor.toString())
        }

        get("/hello") {
            call.respond("Hello, route /hello")
        }
    }
}

