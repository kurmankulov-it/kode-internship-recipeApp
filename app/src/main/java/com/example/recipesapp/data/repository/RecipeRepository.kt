package com.example.recipesapp.data.repository

import com.example.recipesapp.data.database.RecipesDatabase
import com.example.recipesapp.data.database.entities.RecipeDetailsEntity
import com.example.recipesapp.data.database.entities.RecipeEntity
import com.example.recipesapp.data.network.RecipeApi
import com.example.recipesapp.data.network.model.asDataBaseModel
import com.example.recipesapp.data.network.model.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val database: RecipesDatabase,
    private val recipeApi: RecipeApi
){

    fun getAllRecipes(): Flow<List<RecipeEntity>> {
        return database.recipeDao.getAllRecipes()
    }

    fun getAllRecipesByName(): Flow<List<RecipeEntity>> {
        return database.recipeDao.getAllRecipesByName()
    }

    fun getAllRecipesByLastUpdate(): Flow<List<RecipeEntity>> {
        return database.recipeDao.getAllRecipesByLastUpdate()
    }

    fun getRecipesWithSearch(search: String): Flow<List<RecipeEntity>> {
        return database.recipeDao.getRecipesWithSearch(search)
    }

    fun getRecipeDetailsByUuid(uuid: String): Flow<RecipeDetailsEntity> {
        return database.recipeDao.getRecipeDetailsByUuid(uuid)
    }

    suspend fun refreshRecipes() {
        withContext(Dispatchers.IO) {
            val recipeList = recipeApi.getRecipeList()
            database.recipeDao.insertRecipes(recipeList.asDatabaseModel())
        }
    }

    suspend fun refreshRecipeDetails(uuid: String) {
        withContext(Dispatchers.IO) {
            val recipeDetailsList = recipeApi.getRecipeDetails(uuid)
            database.recipeDao.insertRecipeDetails(recipeDetailsList.asDataBaseModel())
        }
    }

    fun exists(uuid: String): Flow<Boolean> {
        return database.recipeDao.exists(uuid)
    }

}

