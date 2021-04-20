package com.example.recipesapp.presentation.ui.recipeslist

import androidx.lifecycle.*
import com.example.recipesapp.domain.interaction.recipe.*
import com.example.recipesapp.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
@ExperimentalCoroutinesApi
class RecipeListViewModel @Inject constructor(
        private val getAllRecipesUseCase: GetAllRecipesUseCase,
        private val refreshRecipesUseCase: RefreshRecipesUseCase,
        private val getRecipesWithSearchUseCase: GetRecipesWithSearchUseCase,
        private val getAllRecipesByNameUseCase: GetAllRecipesByNameUseCase,
        private val getAllRecipesByLastUpdateUseCase: GetAllRecipesByLastUpdateUseCase
) : ViewModel() {

    private val _recipes: MutableLiveData<List<Recipe>> = MutableLiveData()

    val recipes: LiveData<List<Recipe>> get() = _recipes

    val isLoaded: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        refreshDataFromRepository()
        getAllRecipes()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                refreshRecipesUseCase.refresh()
                isLoaded.value = true
            } catch (networkError: IOException) {
                // TODO: No internet connection notification
            }
        }
    }

    fun getAllRecipes() {
        viewModelScope.launch {
            getAllRecipesUseCase.getAll().collect {
                _recipes.value = it
            }
        }
    }

    fun getAllRecipesByName() {
        viewModelScope.launch {
            getAllRecipesByNameUseCase.getAll().collect {
                _recipes.value = it
            }
        }
    }

    fun getAllRecipesByLastUpdate() {
        viewModelScope.launch {
            getAllRecipesByLastUpdateUseCase.getAll().collect {
                _recipes.value = it
            }
        }
    }

    fun getRecipesWithSearch(search: String) {
        viewModelScope.launch {
            getRecipesWithSearchUseCase.find(search).collect {
                _recipes.value = it
            }
        }
    }
}