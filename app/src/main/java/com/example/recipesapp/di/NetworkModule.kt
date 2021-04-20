package com.example.recipesapp.di

import com.example.recipesapp.data.network.RecipeApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRecipeApi(): RecipeApi {
        return Retrofit.Builder()
                .baseUrl("https://test.kode-t.ru/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(RecipeApi::class.java)
    }
}