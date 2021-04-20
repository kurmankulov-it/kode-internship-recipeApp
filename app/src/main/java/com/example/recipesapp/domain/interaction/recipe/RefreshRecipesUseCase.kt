package com.example.recipesapp.domain.interaction.recipe

import com.example.recipesapp.data.repository.RecipeRepository
import javax.inject.Inject

class RefreshRecipesUseCase @Inject constructor(
        private val recipeRepository: RecipeRepository
) {
    suspend fun refresh() {
        recipeRepository.refreshRecipes()
    }
}