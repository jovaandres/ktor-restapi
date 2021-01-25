package com.example.ktorproject.entity

import com.example.ktorproject.entity.CustomerReviewTable.autoIncrement
import org.jetbrains.exposed.sql.Table

object DetailRestaurant : Table() {
    val id = varchar("id", 64)
    val name = varchar("name", 64)
    val description = text("description")
    val city = varchar("city", 64)
    val address = varchar("address", 64)
    val pictureId = varchar("pictureId", 8)
    val rating = double("rating")
    override val primaryKey = PrimaryKey(id)
}

object CategoryTable : Table() {
    val key = integer("key").autoIncrement()
    val id = varchar("id", 64)
    val name = varchar("name", 64)
    override val primaryKey = PrimaryKey(key)
}

object CustomerReviewTable : Table() {
    val key = integer("key").autoIncrement()
    val id = varchar("id", 64)
    val name = varchar("name", 64)
    val review = varchar("review", 64)
    val date = varchar("date", 64)
    override val primaryKey = PrimaryKey(key)
}

object FoodsTable : Table() {
    val key = integer("key").autoIncrement()
    val id = varchar("id", 64)
    val name = varchar("name", 64)
    override val primaryKey = PrimaryKey(key)
}

object DrinksTable : Table() {
    val key = integer("key").autoIncrement()
    val id = varchar("id", 64)
    val name = varchar("name", 64)
    override val primaryKey = PrimaryKey(key)
}
