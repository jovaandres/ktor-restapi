package com.example.ktorproject

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/user").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}
