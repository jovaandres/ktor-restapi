package com.example.ktorproject

import org.jetbrains.exposed.sql.Table


object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 222)
    val registerDate = long("registerDate")
    override val primaryKey = PrimaryKey(id)
}

data class User(
        val id: Int,
        val name: String,
        val registerDate: Long
)