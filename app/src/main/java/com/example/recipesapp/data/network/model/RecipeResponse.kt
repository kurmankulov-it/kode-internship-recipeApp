package com.example.recipesapp.data.network.model

import com.example.recipesapp.data.database.entities.RecipeEntity

data class RecipeResponseContainer(val recipes: List<RecipeResponse>)

data class RecipeResponse(
    val uuid: String,
    val name: String,
    val images: List<String>?,
    val lastUpdated: Int,
    val description: String?,
    val instructions: String,
    val difficulty: Int)

/*fun RecipeResponseContainer.asDomainModel(): List<Recipe> {
    return recipes.map {
        Recipe(
            name = it.name,
            images = it.images,
            description = it.description,
            instructions = it.instructions,
            difficulty = it.difficulty
        )
    }
} */

fun RecipeResponseContainer.asDatabaseModel(): List<RecipeEntity> {
    return recipes.map {
        RecipeEntity(
            uuid = it.uuid,
            name = it.name,
            images = it.images,
            lastUpdated = it.lastUpdated,
            description = it.description,
            instructions = it.instructions,
            difficulty = it.difficulty
        )
    }
}