package com.example.ktorproject

import org.jetbrains.exposed.sql.Table

object ListResponse: Table() {
    val id = integer("id").autoIncrement()
    val error = bool("error")
    val message = varchar("message", 64)
    val count = integer("count")
    override val primaryKey = PrimaryKey(id)
}

object ListRestaurant: Table() {
    val id = varchar("id", 64)
    val name = varchar("name", 64)
    val description = text("description")
    val pictureId = varchar("pictureId", 8)
    val city = varchar("city", 64)
    val rating = double("rating")
    override val primaryKey = PrimaryKey(id)
}

data class Restaurants(
    val error: Boolean,
    val message: String,
    val count: Int,
    var restaurants: List<Restaurant>?
)

data class Restaurant(
    val id: String,
    val name: String,
    val description: String,
    val pictureId: String,
    val city: String,
    val rating: Double
)