package com.example.recipesapp.di

import com.example.recipesapp.data.database.RecipesDatabase
import com.example.recipesapp.data.network.RecipeApi
import com.example.recipesapp.data.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(recipesDatabase: RecipesDatabase,
                                recipeApi: RecipeApi): RecipeRepository {
        return RecipeRepository(recipesDatabase, recipeApi)
    }
}