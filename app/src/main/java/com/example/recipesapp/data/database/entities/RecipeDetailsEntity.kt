package com.example.recipesapp.data.database.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipesapp.domain.model.RecipeDetails

@Entity(tableName = "recipe_details")
data class RecipeDetailsEntity constructor(
        @PrimaryKey
        @NonNull
        val uuid: String,
        val name: String,
        val images: List<String>,
        val lastUpdated: Int,
        val description: String?,
        val instructions: String,
        val difficulty: Int,
        val similar: List<RecipeBrief>
)

data class RecipeBrief constructor(
        val uuid: String,
        val name: String,
        val image: String
)

fun RecipeDetailsEntity.asDomainModel(): RecipeDetails {
    return RecipeDetails(
            name = name,
            images = images,
            lastUpdated = lastUpdated,
            description = description,
            instructions = instructions,
            difficulty = difficulty,
            similar = similar
    )
}