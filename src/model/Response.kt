package com.example.ktorproject.model

data class Response(
    val error: Boolean,
    val message: String,
    val count: Int,
    var restaurants: List<Restaurant>?
)
