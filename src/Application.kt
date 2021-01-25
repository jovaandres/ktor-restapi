package com.example.ktorproject

import com.example.ktorproject.controller.RestaurantService
import com.example.ktorproject.database.DatabaseFactory
import com.example.ktorproject.model.User
import com.example.ktorproject.route.userRoute
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start()
}

@Suppress("unused") // Referenced in application.conf
fun Application.module(testing: Boolean = false) {
    install(DefaultHeaders)
    install(CallLogging)
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }
    install(Authentication) {
        jwt {
            verifier(JWTConfig.verfier)
            realm = "com.example"
            validate {
                val name = it.payload.getClaim("name").asString()
                val password = it.payload.getClaim("password").asString()
                if (name != null && password != null) {
                    User(name, password)
                } else {
                    null
                }
            }
        }
    }

    DatabaseFactory.init()

    val userService = RestaurantService()

    install(Routing) {
        userRoute(userService)
    }
}

