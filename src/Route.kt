package com.example.ktorproject

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userRoute(userService: UserService) {

    route("/user") {
        get("/list") {
            call.respond(userService.getALlRestaurants())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalStateException("Must provided id")
            val user = userService.getUser(id)
            if (user == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(user)
        }

        post("/add") {
            val user = call.receive<User>()
            userService.addUser(user)
            call.respond(HttpStatusCode.Accepted)
        }
    }
}