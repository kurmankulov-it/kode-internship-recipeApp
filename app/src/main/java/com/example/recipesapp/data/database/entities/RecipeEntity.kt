package com.example.recipesapp.data.database.entities

import androidx.annotation.NonNull
import androidx.room.*
import com.example.recipesapp.domain.model.Recipe

@Entity(tableName = "recipe")
data class RecipeEntity constructor(
    @PrimaryKey
    @NonNull
    val uuid: String,
    val name: String,
    val images: List<String>?,
    val lastUpdated: Int,
    val description: String?,
    val instructions: String,
    val difficulty: Int
)

fun List<RecipeEntity>.asDomainModel(): List<Recipe> {
    return map {
        Recipe(
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