package com.example.ktorproject

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