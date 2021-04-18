package com.example.recipesapp.domain.interaction.recipe

import com.example.recipesapp.data.repository.RecipeRepository
import javax.inject.Inject

class RefreshRecipeDetailsUseCase @Inject constructor(
        private val recipeRepository: RecipeRepository
) {
    suspend fun refresh(uuid: String) {
        recipeRepository.refreshRecipeDetails(uuid)
    }
}