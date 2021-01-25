package com.example.ktorproject.route

import com.example.ktorproject.JWTConfig
import com.example.ktorproject.controller.RestaurantService
import com.example.ktorproject.model.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userRoute(userService: RestaurantService) {

    route("/") {
        get("/") {
            try {
                val restaurant = userService.getListRestaurant()
                val response = Response(
                    error = false,
                    message = "success",
                    count = restaurant.size,
                    restaurants = restaurant
                )
                call.respond(response)
            } catch (e: Exception) {
                val response = Response(
                    error = true,
                    message = e.message.toString(),
                    count = 0,
                    restaurants = null
                )
                call.respond(response)
            }
        }

        get("/search/{query}") {
            val query = call.parameters["query"] ?: throw IllegalStateException("Must provided query")
            try {
                val restaurant = userService.searchRestaurant(query)
                val response = Response(
                    error = false,
                    message = "success",
                    count = restaurant?.size ?: 0,
                    restaurants = restaurant
                )
                call.respond(response)
            } catch (e: Exception) {
                val response = Response(
                    error = true,
                    message = e.message.toString(),
                    count = 0,
                    restaurants = null
                )
                call.respond(response)
            }
        }

        get("/detail/{id}") {
            val id = call.parameters["id"] ?: throw IllegalStateException("Must provided id")
            try {
                val detail = userService.getRestaurantDetail(id)
                val categories = userService.getCategory(id)
                val reviews = userService.getReview(id)
                val foods = userService.getFoods(id)
                val drinks = userService.getDrinks(id)
                detail?.categories = categories
                detail?.customerReviews = reviews
                detail?.menus = Menus(
                    foods = foods,
                    drinks = drinks
                )
                call.respond(detail as RestaurantDetail)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        post("/generate_token") {
            val user = call.receive<User>()
            val token = JWTConfig.generateToken(user)
            call.respond("Username: ${user.name} \nToken: $token\n" +
                    "Add token to header => Authorization: Bearer <YOUR-TOKEN>")
        }

        authenticate {

            post("/add") {
                val restaurant = call.receive<Restaurant>()
                try {
                    userService.addRestaurant(restaurant)
                    call.respond(HttpStatusCode.Accepted)
                } catch (e: Exception) {
                    call.respond(e.message.toString())
                }
            }

            post("/detail/add") {
                val restaurantDetail = call.receive<RestaurantDetail>()
                try {
                    userService.addDetailRestaurant(restaurantDetail)
                    call.respond(HttpStatusCode.Accepted)
                } catch (e: Exception) {
                    call.respond(e.message.toString())
                }
            }

            post("/review") {
                val review = call.receive<PostReview>()
                try {
                    userService.addReview(review)
                    call.respond(HttpStatusCode.Accepted)
                } catch (e: Exception) {
                    call.respond(e.message.toString())
                }
            }
        }
    }
}

val ApplicationCall.user get() = authentication.principal<User>()