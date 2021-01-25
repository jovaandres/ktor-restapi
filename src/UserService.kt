package com.example.ktorproject

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserService {

    suspend fun getListResponse(): Restaurants = newSuspendedTransaction {
        ListResponse.selectAll().mapNotNull { toListResponse(it) }.first()
    }

    suspend fun getListRestaurant(): List<Restaurant> = newSuspendedTransaction {
        ListRestaurant.selectAll().mapNotNull { toListRestaurant(it) }
    }

    suspend fun addRestaurant(restaurant: Restaurant) = newSuspendedTransaction {
        ListRestaurant.insert {
            it[id] = restaurant.id
            it[name] = restaurant.name
            it[description] = restaurant.description
            it[pictureId] = restaurant.pictureId
            it[city] = restaurant.city
            it[rating] = restaurant.rating
        }
    }

    suspend fun searchRestaurant(query: String): List<Restaurant>? = newSuspendedTransaction {
        ListRestaurant.select {
            (ListRestaurant.name.lowerCase().like("%${query.toLowerCase()}%"))
        }.mapNotNull { toListRestaurant(it) }
    }

    private fun toListResponse(row: ResultRow): Restaurants {
        return Restaurants(
            error = row[ListResponse.error],
            message = row[ListResponse.message],
            count = row[ListResponse.count],
            null
        )
    }

    private fun toListRestaurant(row: ResultRow): Restaurant {
        return Restaurant(
            id = row[ListRestaurant.id],
            name = row[ListRestaurant.name],
            description = row[ListRestaurant.description],
            pictureId = row[ListRestaurant.pictureId],
            city = row[ListRestaurant.city],
            rating = row[ListRestaurant.rating]
        )
    }
}