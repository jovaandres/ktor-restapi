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
}