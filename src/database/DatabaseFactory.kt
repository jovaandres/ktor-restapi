package com.example.ktorproject.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

object DatabaseFactory {

    fun init() {
        val url =
            "jdbc:postgresql://${System.getenv("HOST")}:5432/${System.getenv("DATABASE")}?sslmode=require"
        Database.connect(
            url,
            "org.postgresql.Driver",
            System.getenv("USERNAME"),
            System.getenv("PASSWORD"),
        )
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig("/hikari.properties")
        return HikariDataSource(config)
    }
}