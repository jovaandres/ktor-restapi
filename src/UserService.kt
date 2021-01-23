package com.example.ktorproject

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserService {

    suspend fun getAllUsers(): List<User> = newSuspendedTransaction {
        Users.selectAll().map { toUser(it) }
    }

    suspend fun getUser(id: Int): User? = newSuspendedTransaction {
        Users.select {
            (Users.id eq id)
        }.mapNotNull { toUser(it) }
            .singleOrNull()
    }

    suspend fun addUser(user: User) = newSuspendedTransaction {
        Users.insert {
            it[name] = user.name
            it[registerDate] = user.registerDate
        }
    }

    private fun toUser(row: ResultRow): User {
        return User(
            id = row[Users.id],
            name = row[Users.name],
            registerDate = row[Users.registerDate]
        )
    }

    fun getALlRestaurants(): Restaurants {
        val restaurants = ArrayList<Restaurant>()
        restaurants.addAll(
            arrayListOf(
                Restaurant(
                    "rqdv5juczeskfw1e867",
                    "Melting Pot",
                    "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.",
                    "14",
                    "Medan",
                    4.2
                ),
                Restaurant(
                    "rqdv5juczeskfw1e867",
                    "Melting Pot",
                    "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet.",
                    "14",
                    "Medan",
                    4.2
                ),
            )
        )
        return Restaurants(
            false,
            "success",
            20,
            restaurants
        )
    }
}