package com.andreromano.foodmix.network

import com.andreromano.foodmix.domain.model.*
import java.util.*
import kotlin.math.roundToInt

object FakeData {

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return Math.round(this * multiplier) / multiplier
    }

    private val rng = Random(1)

    private val id
        get() = rng.nextLong().toString()

    val imageUrl = "https://picsum.photos/200/300"

    private fun newCategory(): Category = id.let { Category(it, "category $it", imageUrl) }

    val categories: List<Category> = (0..7).map {
        newCategory()
    }

    private fun generateRandomCategories(): List<Category> {
        val randomNumber1 = (rng.nextDouble() * categories.size).toInt()
        val randomNumber2 = (rng.nextDouble() * categories.size).toInt()
        val randomNumber3 = (rng.nextDouble() * categories.size).toInt()

        return categories.filterIndexed { index, _ -> index == randomNumber1 || index == randomNumber2 || index == randomNumber3 }
    }

    val directions: List<Direction> = (0..8).map {
        Direction("description $id", imageUrl)
    }

    private fun generateRandomDirections(): List<Direction> {
        val randomNumber1 = (rng.nextDouble() * directions.size).toInt()
        val randomNumber2 = (rng.nextDouble() * directions.size).toInt()
        val randomNumber3 = (rng.nextDouble() * directions.size).toInt()

        return directions.filterIndexed { index, _ -> index == randomNumber1 || index == randomNumber2 || index == randomNumber3 }
    }

    val ingredients: List<String> = (0..10).map {
        "ingredients $id"
    }

    private fun generateRandomIngredients(): List<String> {
        val randomNumber1 = (rng.nextDouble() * ingredients.size).toInt()
        val randomNumber2 = (rng.nextDouble() * ingredients.size).toInt()
        val randomNumber3 = (rng.nextDouble() * ingredients.size).toInt()
        val randomNumber4 = (rng.nextDouble() * ingredients.size).toInt()

        return ingredients.filterIndexed { index, _ -> index == randomNumber1 || index == randomNumber2 || index == randomNumber3 || index == randomNumber4 }
    }

    val users: List<User> = (0..8).map {
        User(id, "username $id", imageUrl)
    }

    private fun generateRandomUser(): User {
        val randomNumber1 = (rng.nextDouble() * users.size).toInt()

        return users.filterIndexed { index, _ -> index == randomNumber1 }.first()
    }

    val reviews: List<Review> = (0..10).map {
        Review(id, generateRandomUser(), "comment $id", System.currentTimeMillis(), (rng.nextDouble() * 100).toInt(), rng.nextBoolean() && rng.nextBoolean())
    }

    private fun generateRandomReviews(): List<Review> {
        val randomNumber1 = (rng.nextDouble() * reviews.size).toInt()
        val randomNumber2 = (rng.nextDouble() * reviews.size).toInt()
        val randomNumber3 = (rng.nextDouble() * reviews.size).toInt()
        val randomNumber4 = (rng.nextDouble() * reviews.size).toInt()

        return reviews.filterIndexed { index, _ -> index == randomNumber1 || index == randomNumber2 || index == randomNumber3 || index == randomNumber4 }
    }

    private fun generateRandomRecipe(): Recipe = id.let {
        Recipe(
            id,
            "Title $id",
            "Description $id",
            rng.nextBoolean() && rng.nextBoolean(),
            imageUrl,
            (rng.nextDouble() * 5).round(1),
            (rng.nextDouble() * 100).roundToInt(),
            (rng.nextDouble() * 1000).roundToInt(),
            (rng.nextDouble() * 6).roundToInt(),
            (rng.nextDouble() * 100).roundToInt(),
            generateRandomCategories(),
            generateRandomIngredients(),
            generateRandomDirections(),
            generateRandomReviews()
        )
    }

    val recipes = (0..100).map {
        generateRandomRecipe()
    }

    var shouldFail: Boolean = false

}