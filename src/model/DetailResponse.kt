package com.example.ktorproject.model

data class DetailResponse(
    val error: Boolean,
    val message: String,
    val restaurant: RestaurantDetail?
)