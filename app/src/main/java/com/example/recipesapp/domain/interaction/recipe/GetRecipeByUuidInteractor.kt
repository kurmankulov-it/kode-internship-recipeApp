package com.example.recipesapp.domain.interaction.recipe

import com.example.recipesapp.data.database.entities.asDomainModel
import com.example.recipesapp.data.repository.RecipeRepository
import com.example.recipesapp.domain.model.RecipeDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetRecipeByUuidInteractor @Inject constructor(
        private val recipeRepository: RecipeRepository
) {
    fun get(uuid: String): Flow<RecipeDetails> = recipeRepository.getRecipeDetailsByUuid(uuid).map {
        it.asDomainModel()
    }.flowOn(Dispatchers.Default)

    fun exists(uuid: String): Flow<Boolean> = recipeRepository.exists(uuid).flowOn(Dispatchers.Default)
}