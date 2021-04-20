package com.example.recipesapp.domain.model

data class Recipe(
        val uuid: String,
        val name: String,
        val images: List<String>,
        val lastUpdated: Int,
        val description: String?,
        val instructions: String,
        val difficulty: Int)