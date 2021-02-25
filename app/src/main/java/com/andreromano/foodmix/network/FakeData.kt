package com.andreromano.foodmix.network

import com.andreromano.foodmix.domain.model.IngredientType
import com.andreromano.foodmix.network.model.*
import java.util.*
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

object FakeData {

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return Math.round(this * multiplier) / multiplier
    }

    private val rng = Random(1)

    private val id
        get() = rng.nextLong().absoluteValue.toInt()

    private val imageUrl
        get() = "https://picsum.photos/200/300?id=${Random().nextInt()}"

    private fun newCategory(): CategoryResult = id.let { CategoryResult(it, "category $it", imageUrl) }

    val categories: List<CategoryResult> = (0..7).map {
        newCategory()
    }

    private fun generateRandomCategories(): List<CategoryResult> {
        val randomNumber1 = (rng.nextDouble() * categories.size).toInt()
        val randomNumber2 = (rng.nextDouble() * categories.size).toInt()
        val randomNumber3 = (rng.nextDouble() * categories.size).toInt()

        return categories.filterIndexed { index, _ -> index == randomNumber1 || index == randomNumber2 || index == randomNumber3 }
    }

    val ingredientTypes: List<String> = (0..20).map {
        "Type $id"
    }

    val directions: List<DirectionResult> = (0..8).map {
        it.let { id -> DirectionResult(id, "title $id", "description $id", imageUrl) }
    }

    private fun generateRandomDirections(): List<DirectionResult> {
        val randomNumber1 = (rng.nextDouble() * directions.size).toInt()
        val randomNumber2 = (rng.nextDouble() * directions.size).toInt()
        val randomNumber3 = (rng.nextDouble() * directions.size).toInt()

        return directions.filterIndexed { index, _ -> index == randomNumber1 || index == randomNumber2 || index == randomNumber3 }
    }

    val ingredients: List<IngredientResult> = (0..10).map {
        val randomNumber = (rng.nextDouble() * ingredientTypes.size).toInt()
        IngredientResult(id, "ingredients $id", imageUrl, ingredientTypes[randomNumber])
    }

    private fun generateRandomIngredients(): List<IngredientResult> {
        val randomNumber1 = (rng.nextDouble() * ingredients.size).toInt()
        val randomNumber2 = (rng.nextDouble() * ingredients.size).toInt()
        val randomNumber3 = (rng.nextDouble() * ingredients.size).toInt()
        val randomNumber4 = (rng.nextDouble() * ingredients.size).toInt()

        return ingredients.filterIndexed { index, _ -> index == randomNumber1 || index == randomNumber2 || index == randomNumber3 || index == randomNumber4 }
    }

    val users: List<UserResult> = (0..8).map {
        UserResult(id, "username $id", imageUrl)
    }

    private fun generateRandomUser(): UserResult {
        val randomNumber1 = (rng.nextDouble() * users.size).toInt()

        return users.filterIndexed { index, _ -> index == randomNumber1 }.first()
    }

    val reviews: List<ReviewResult> = (0..10).map {
        ReviewResult(id, generateRandomUser(), "comment $id", System.currentTimeMillis(), (rng.nextDouble() * 100).toInt(), rng.nextBoolean() && rng.nextBoolean())
    }

    private fun generateRandomReviews(): List<ReviewResult> {
        val randomNumber1 = (rng.nextDouble() * reviews.size).toInt()
        val randomNumber2 = (rng.nextDouble() * reviews.size).toInt()
        val randomNumber3 = (rng.nextDouble() * reviews.size).toInt()
        val randomNumber4 = (rng.nextDouble() * reviews.size).toInt()

        return reviews.filterIndexed { index, _ -> index == randomNumber1 || index == randomNumber2 || index == randomNumber3 || index == randomNumber4 }
    }

    private fun generateRandomRecipe(): RecipeResult = id.let {
        RecipeResult(
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

    val userProfile: UserProfileResult = UserProfileResult(
        id = 1,
        username = "username",
        description = "some description lorem ipsum dolore mais cenas que nao me lembro",
        avatarUrl = imageUrl,
        backgroundUrl = imageUrl,
        totalRecipesCount = 23,
        totalCookbooks = 2,
        myRecipesCount = 12,
        myCookbooksCount = 1,
        shoppingListCount = 17
    )

    var shouldFail: Boolean = false

}