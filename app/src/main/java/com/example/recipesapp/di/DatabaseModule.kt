package com.example.recipesapp.di

import android.app.Application
import androidx.room.Room
import com.example.recipesapp.data.database.RecipesDatabase
import com.example.recipesapp.data.database.dao.RecipeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): RecipesDatabase {
        return Room.databaseBuilder(
            app,
            RecipesDatabase::class.java,
            "recipes"
        ).build()
    }

    @Singleton
    @Provides
    fun provideRecipeDao(database: RecipesDatabase): RecipeDao {
        return database.recipeDao
    }
}