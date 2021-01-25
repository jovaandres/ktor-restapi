package com.example.ktorproject.controller

import com.example.ktorproject.entity.*
import com.example.ktorproject.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.text.SimpleDateFormat

class RestaurantService {

    suspend fun getListRestaurant(): List<Restaurant> = newSuspendedTransaction {
        ListRestaurant.selectAll().mapNotNull { toListRestaurant(it) }
    }

    suspend fun getRestaurantDetail(id: String): RestaurantDetail? = newSuspendedTransaction {
        DetailRestaurant.select {
            (DetailRestaurant.id eq id)
        }.mapNotNull { toRestaurantDetail(it) }
            .singleOrNull()
    }

    suspend fun getCategory(id: String): List<Category>? = newSuspendedTransaction {
        CategoryTable.select {
            (CategoryTable.id eq id)
        }.mapNotNull { toCategory(it) }
    }

    suspend fun getReview(id: String): List<CustomerReview>? = newSuspendedTransaction {
        CustomerReviewTable.select {
            (CustomerReviewTable.id eq id)
        }.mapNotNull { toReview(it) }
    }

    suspend fun getFoods(id: String): List<Foods>? = newSuspendedTransaction {
        FoodsTable.select {
            (FoodsTable.id eq id)
        }.mapNotNull { toFood(it) }
    }

    suspend fun getDrinks(id: String): List<Drinks>? = newSuspendedTransaction {
        DrinksTable.select {
            (DrinksTable.id eq id)
        }.mapNotNull { toDrink(it) }
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

    suspend fun addDetailRestaurant(restaurantDetail: RestaurantDetail) = newSuspendedTransaction {
        DetailRestaurant.insert {
            it[id] = restaurantDetail.id
            it[name] = restaurantDetail.name
            it[description] = restaurantDetail.description
            it[city] = restaurantDetail.city
            it[address] = restaurantDetail.address
            it[pictureId] = restaurantDetail.pictureId
            it[rating] = restaurantDetail.rating
        }
        restaurantDetail.categories?.forEach { category ->
            CategoryTable.insert {
                it[id] = restaurantDetail.id
                it[name] = category.name
            }
        }
        restaurantDetail.customerReviews?.forEach { userReview ->
            CustomerReviewTable.insert {
                it[id] = restaurantDetail.id
                it[name] = userReview.name
                it[review] = userReview.review
                it[date] = userReview.date
            }
        }
        restaurantDetail.menus?.foods?.forEach { foods ->
            FoodsTable.insert {
                it[id] = restaurantDetail.id
                it[name] = foods.name
            }
        }
        restaurantDetail.menus?.drinks?.forEach { drinks ->
            DrinksTable.insert {
                it[id] = restaurantDetail.id
                it[name] = drinks.name
            }
        }
    }

    suspend fun addReview(postReview: PostReview) = newSuspendedTransaction {
        CustomerReviewTable.insert {
            it[id] = postReview.id
            it[name] = postReview.name
            it[review] = postReview.review
            it[date] = run {
                val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy")
                val time = System.currentTimeMillis()
                simpleDateFormat.format(time)
            }
        }
    }

    suspend fun deleteRestaurant(id: String) = newSuspendedTransaction {
        ListRestaurant.deleteWhere { ListRestaurant.id eq id }
    }

    suspend fun searchRestaurant(query: String): List<Restaurant>? = newSuspendedTransaction {
        ListRestaurant.select {
            (ListRestaurant.name.lowerCase().like("%${query.toLowerCase()}%"))
        }.mapNotNull { toListRestaurant(it) }
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

    private fun toRestaurantDetail(row: ResultRow): RestaurantDetail {
        return RestaurantDetail(
            id = row[DetailRestaurant.id],
            name = row[DetailRestaurant.name],
            description = row[DetailRestaurant.description],
            city = row[DetailRestaurant.city],
            address = row[DetailRestaurant.address],
            pictureId = row[DetailRestaurant.pictureId],
            categories = null,
            menus = null,
            rating = row[DetailRestaurant.rating],
            customerReviews = null
        )
    }

    private fun toCategory(row: ResultRow): Category {
        return Category(
            name = row[CategoryTable.name]
        )
    }

    private fun toReview(row: ResultRow): CustomerReview {
        return CustomerReview(
            name = row[CustomerReviewTable.name],
            review = row[CustomerReviewTable.review],
            date = row[CustomerReviewTable.date]
        )
    }

    private fun toFood(row: ResultRow): Foods {
        return Foods(
            name = row[FoodsTable.name]
        )
    }

    private fun toDrink(row: ResultRow): Drinks {
        return Drinks(
            name = row[DrinksTable.name]
        )
    }
}