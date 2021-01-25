package com.example.ktorproject.model

data class Restaurant(
    val id: String,
    val name: String,
    val description: String,
    val pictureId: String,
    val city: String,
    val rating: Double
)