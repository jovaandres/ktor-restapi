package com.example.ktorproject.entity

import org.jetbrains.exposed.sql.Table

object ListRestaurant: Table() {
    val id = varchar("id", 64)
    val name = varchar("name", 64)
    val description = text("description")
    val pictureId = varchar("pictureId", 8)
    val city = varchar("city", 64)
    val rating = double("rating")
    override val primaryKey = PrimaryKey(id)
}