package com.example.recipesapp.domain.interaction.recipe

import com.example.recipesapp.data.database.entities.asDomainModel
import com.example.recipesapp.data.repository.RecipeRepository
import com.example.recipesapp.domain.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllRecipesByNameUseCase @Inject constructor(
        private val recipeRepository: RecipeRepository
) {
    fun getAll(): Flow<List<Recipe>> = recipeRepository.getAllRecipesByName().map {
        it.asDomainModel()
    }.flowOn(Dispatchers.Default)
}