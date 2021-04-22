package com.example.recipesapp.presentation.ui.recipeslist

import androidx.lifecycle.*
import com.example.recipesapp.domain.interaction.recipe.*
import com.example.recipesapp.domain.model.Recipe
import com.example.recipesapp.util.Resource
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
        private val getAllRecipesByLastUpdateUseCase: GetAllRecipesByLastUpdateUseCase,
        private val getAnyRecipeUseCase: GetAnyRecipeUseCase
) : ViewModel() {

    private val _recipes: MutableLiveData<Resource<List<Recipe>>> = MutableLiveData()
    val recipes: LiveData<Resource<List<Recipe>>> get() = _recipes

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _recipes.value = Resource.Loading()
            try {
                refreshRecipesUseCase.refresh()
                getAllRecipes()
            } catch (networkError: IOException) {
                getAnyRecipeUseCase.getAnyRecipe().collect { isExist ->
                    if (!isExist) {
                        _recipes.value = Resource.Error("Items not found")
                    } else {
                        getAllRecipes()
                    }
                }
            }
        }
    }

    fun getAllRecipes() {
        viewModelScope.launch {
            getAllRecipesUseCase.getAll().collect {
                _recipes.value = Resource.Success(it)
            }
        }
    }

    fun getAllRecipesByName() {
        viewModelScope.launch {
            getAllRecipesByNameUseCase.getAll().collect {
                _recipes.value = Resource.Success(it)
            }
        }
    }

    fun getAllRecipesByLastUpdate() {
        viewModelScope.launch {
            getAllRecipesByLastUpdateUseCase.getAll().collect {
                _recipes.value = Resource.Success(it)
            }
        }
    }

    fun getRecipesWithSearch(search: String) {
        viewModelScope.launch {
            getRecipesWithSearchUseCase.find(search).collect {
                _recipes.value = Resource.Success(it)
            }
        }
    }
}