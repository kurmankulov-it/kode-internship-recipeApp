package com.example.recipesapp.domain.interaction.recipe

import com.example.recipesapp.data.database.entities.asDomainModel
import com.example.recipesapp.data.repository.RecipeRepository
import com.example.recipesapp.domain.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetAllRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    fun getAll(): Flow<List<Recipe>> = recipeRepository.getAllRecipes().map {
        it.asDomainModel()
    }.flowOn(Dispatchers.Default)
}