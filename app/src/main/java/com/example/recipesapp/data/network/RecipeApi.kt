package com.example.recipesapp.data.network

import com.example.recipesapp.data.network.model.RecipeDetailsResponseContainer
import com.example.recipesapp.data.network.model.RecipeResponseContainer
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApi {
    @GET("recipes")
    suspend fun getRecipeList(): RecipeResponseContainer

    @GET("recipes/{uuid}")
    suspend fun getRecipeDetails(@Path("uuid") uuid: String): RecipeDetailsResponseContainer
}