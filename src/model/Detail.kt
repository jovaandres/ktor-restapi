package com.example.ktorproject.model

data class RestaurantDetail(
    val id: String,
    val name: String,
    val description: String,
    val city: String,
    val address: String,
    val pictureId: String,
    var categories: List<Category>?,
    var menus: Menus?,
    val rating: Double,
    var customerReviews: List<CustomerReview>?
)

data class Category(
    val name: String
)

data class PostReview(
    val id: String,
    val name: String,
    val review: String
)

data class CustomerReview(
    val name: String,
    val review: String,
    val date: String
)

data class Foods(
    val name: String
)

data class Drinks(
    val name: String
)

data class Menus(
    val foods: List<Foods>?,
    val drinks: List<Drinks>?
)