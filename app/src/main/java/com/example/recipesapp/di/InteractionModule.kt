package com.example.recipesapp.di

import com.example.recipesapp.data.repository.RecipeRepository
import com.example.recipesapp.domain.interaction.recipe.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractionModule {

    @Singleton
    @Provides
    fun provideGetAllRecipesUseCase(recipeRepository: RecipeRepository) : GetAllRecipesUseCase {
        return GetAllRecipesUseCase(recipeRepository)
    }

    @Singleton
    @Provides
    fun provideRefreshRecipesUseCase(recipeRepository: RecipeRepository) : RefreshRecipesUseCase {
        return RefreshRecipesUseCase(recipeRepository)
    }

    @Singleton
    @Provides
    fun provideFindRecipesWithSearchUseCase(recipeRepository: RecipeRepository): GetRecipesWithSearchUseCase {
        return GetRecipesWithSearchUseCase(recipeRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllRecipesByNameUseCase(recipeRepository: RecipeRepository): GetAllRecipesByNameUseCase {
        return GetAllRecipesByNameUseCase(recipeRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllRecipesByLastUpdateUseCase(recipeRepository: RecipeRepository): GetAllRecipesByLastUpdateUseCase {
        return GetAllRecipesByLastUpdateUseCase(recipeRepository)
    }

    @Singleton
    @Provides
    fun provideRefreshRecipeDetailsUseCase(recipeRepository: RecipeRepository): RefreshRecipeDetailsUseCase {
        return RefreshRecipeDetailsUseCase(recipeRepository)
    }

    @Singleton
    @Provides
    fun provideGetRecipeByUuidUseCase(recipeRepository: RecipeRepository): GetRecipeByUuidUseCase {
        return GetRecipeByUuidUseCase(recipeRepository)
    }
}