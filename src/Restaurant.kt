package com.example.ktorproject

import org.jetbrains.exposed.sql.Table

object ListResponse: Table() {
    val error = bool("error")
    val message = varchar("message", 64)
    val count = long("count")
}

object ListRestaurant: Table() {
    val id = varchar("id", 64)
    val name = varchar("name", 64)
    val description = varchar("description", 255)
    val pictureId = varchar("pictureId", 8)
    val city = varchar("city", 64)
    val rating = double("rating")
}

data class Restaurants(
    val error: Boolean,
    val message: String,
    val count: Long,
    val restaurants: List<Restaurant>
)

data class Restaurant(
    val id: String,
    val name: String,
    val description: String,
    val pictureId: String,
    val city: String,
    val rating: Double
)