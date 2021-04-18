package com.example.recipesapp.domain.model

import com.example.recipesapp.data.database.entities.RecipeBrief

data class RecipeDetails(
        val name: String,
        val images: List<String>?,
        val lastUpdated: Int,
        val description: String?,
        val instructions: String,
        val difficulty: Int,
        val similar: List<RecipeBrief>)