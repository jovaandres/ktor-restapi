package com.example.ktorproject

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.ktorproject.model.User
import java.util.*

object JWTConfig  {
    private const val secret = "my-secret"
    private const val issuer = "com.example"
    private const val validityInMs = 36_000_00 * 24
    private val algorithm = Algorithm.HMAC256(secret)

    val verfier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

    fun generateToken(user: User): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("name", user.name)
        .withClaim("password", user.password)
        .withExpiresAt(getExpiration())
        .sign(algorithm)
}