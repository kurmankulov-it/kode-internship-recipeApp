package com.example.recipesapp.data.database

import androidx.room.*
import com.example.recipesapp.data.database.dao.RecipeDao
import com.example.recipesapp.data.database.entities.RecipeDetailsEntity
import com.example.recipesapp.data.database.entities.RecipeEntity

@Database(entities = [RecipeEntity::class, RecipeDetailsEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class RecipesDatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao
}