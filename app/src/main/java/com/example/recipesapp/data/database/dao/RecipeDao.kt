package com.example.recipesapp.data.database.dao

import androidx.room.*
import com.example.recipesapp.data.database.entities.RecipeDetailsEntity
import com.example.recipesapp.data.database.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(recipes: List<RecipeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeDetails(recipe: RecipeDetailsEntity)

    @Query("select * from recipe")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipe WHERE name LIKE '%' || :search || '%' OR description LIKE '%' || :search || '%' OR instructions LIKE '%' || :search || '%'")
    fun getRecipesWithSearch(search: String): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipe ORDER BY name")
    fun getAllRecipesByName(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipe ORDER BY lastUpdated")
    fun getAllRecipesByLastUpdate(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipe_details WHERE uuid == :uuid")
    fun getRecipeDetailsByUuid(uuid: String): Flow<RecipeDetailsEntity>

    @Query("SELECT EXISTS (SELECT 1 FROM recipe_details WHERE uuid == :uuid)")
    fun exists(uuid: String): Flow<Boolean>

    @Query("SELECT EXISTS (SELECT 1 FROM recipe)")
    fun getAnyRecipe(): Flow<Boolean>
}