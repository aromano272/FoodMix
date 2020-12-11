package com.andreromano.foodmix.database.model

enum class IngredientTypeEntity(
    val tag: String
) {
    MEAT(IngredientTypeEntity.UUID_MEAT),
    FISH(IngredientTypeEntity.UUID_FISH),
    VEGETABLES(IngredientTypeEntity.UUID_VEGETABLES),
    FRUITS(IngredientTypeEntity.UUID_FRUITS),
    GRAINS(IngredientTypeEntity.UUID_GRAINS);


    // UUID used because of persistance together with Moshi and RoomTypeConverters
    companion object {
        //fixme !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //                   WARNING
        //      These TAGs are used in DB Queries
        //   Changing these will require a Migration
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        const val UUID_MEAT = "UUID_MEAT"
        const val UUID_FISH = "UUID_FISH"
        const val UUID_VEGETABLES = "UUID_VEGETABLES"
        const val UUID_FRUITS = "UUID_FRUITS"
        const val UUID_GRAINS = "UUID_GRAINS"
    }
}