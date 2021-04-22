package com.example.recipesapp.domain.interaction.recipe

import com.example.recipesapp.data.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAnyRecipeUseCase @Inject constructor(
        private val recipeRepository: RecipeRepository
) {
    fun getAnyRecipe(): Flow<Boolean> = recipeRepository.getAnyRecipe().flowOn(Dispatchers.Default)
}