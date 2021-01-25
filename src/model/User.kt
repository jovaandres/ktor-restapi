package com.example.ktorproject.model

import io.ktor.auth.*

data class User(
    val name: String,
    val password: String,
    val other: String = "default"
) : Principal