package com.example.ktorproject

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userRoute(userService: UserService) {

    route("/user") {
        get("/") {
            call.respond(userService.getAllUsers())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("Must provided id")
            val user = userService.getUser(id)
            if (user == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(user)
        }
    }
}