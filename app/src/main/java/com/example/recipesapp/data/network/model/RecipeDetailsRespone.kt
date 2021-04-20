package com.example.recipesapp.data.network.model

import com.example.recipesapp.data.database.entities.RecipeBrief
import com.example.recipesapp.data.database.entities.RecipeDetailsEntity

data class RecipeDetailsResponseContainer(val recipe: RecipeDetailsResponse)

class RecipeDetailsResponse(
        val uuid: String,
        val name: String,
        val images: List<String>,
        val lastUpdated: Int,
        val description: String?,
        val instructions: String,
        val difficulty: Int,
        val similar: List<RecipeBrief>)

fun RecipeDetailsResponseContainer.asDataBaseModel(): RecipeDetailsEntity {
    return RecipeDetailsEntity(
            uuid = recipe.uuid,
            name = recipe.name,
            images = recipe.images,
            lastUpdated = recipe.lastUpdated,
            description = recipe.description,
            instructions = recipe.instructions,
            difficulty = recipe.difficulty,
            similar = recipe.similar
    )
}