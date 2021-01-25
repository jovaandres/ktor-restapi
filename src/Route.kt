package com.example.ktorproject

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userRoute(userService: UserService) {

    route("/") {
        get("/") {
            val response = userService.getListResponse()
            val restaurant = userService.getListRestaurant()
            response.restaurants = restaurant
            call.respond(response)
        }

        get("search/{query}") {
            val query = call.parameters["query"] ?: throw IllegalStateException("Must provided query")
            val restaurant = userService.searchRestaurant(query)
            if (restaurant == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(restaurant)
        }

        post("/add") {
            val restaurant = call.receive<Restaurant>()
            userService.addRestaurant(restaurant)
            call.respond(HttpStatusCode.Accepted)
        }
    }
}