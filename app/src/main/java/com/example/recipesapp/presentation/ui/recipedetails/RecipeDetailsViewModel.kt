package com.example.recipesapp.presentation.ui.recipedetails

import androidx.lifecycle.*
import com.example.recipesapp.domain.interaction.recipe.GetRecipeByUuidInteractor
import com.example.recipesapp.domain.interaction.recipe.RefreshRecipeDetailsUseCase
import com.example.recipesapp.domain.model.RecipeDetails
import com.example.recipesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class RecipeDetailsViewModel @Inject constructor(
        private val refreshRecipeDetailsUseCase: RefreshRecipeDetailsUseCase,
        private val getRecipeByUuidInteractor: GetRecipeByUuidInteractor
) : ViewModel() {

    private val _recipe: MutableLiveData<Resource<RecipeDetails>> = MutableLiveData()
    val recipe: LiveData<Resource<RecipeDetails>> get() = _recipe

    fun getRecipeDetails(uuid: String) {
        refreshDataFromRepository(uuid)
    }

    private fun refreshDataFromRepository(uuid: String) {
        viewModelScope.launch {
            _recipe.value = Resource.Loading()
            try {
                refreshRecipeDetailsUseCase.refresh(uuid)
                getRecipe(uuid)
            } catch (networkError: IOException) {
                getRecipeByUuidInteractor.exists(uuid).collect { isExist ->
                    if (!isExist) {
                        _recipe.value = Resource.Error("Item not found")
                    } else {
                        getRecipe(uuid)
                    }
                }
            }

        }
    }

    private fun getRecipe(uuid: String) {
        viewModelScope.launch {
            getRecipeByUuidInteractor.get(uuid).collect {
                _recipe.value = Resource.Success(it)
            }
        }
    }
}