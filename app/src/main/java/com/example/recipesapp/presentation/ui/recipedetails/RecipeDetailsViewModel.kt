package com.example.recipesapp.presentation.ui.recipedetails

import androidx.lifecycle.*
import com.example.recipesapp.domain.interaction.recipe.GetRecipeByUuidInteractor
import com.example.recipesapp.domain.interaction.recipe.RefreshRecipeDetailsUseCase
import com.example.recipesapp.domain.model.RecipeDetails
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

    private val _isExist: MutableLiveData<Boolean> = MutableLiveData(false)
    val isExist: LiveData<Boolean> get() = _isExist

    private val _recipe: MutableLiveData<RecipeDetails> = MutableLiveData()
    val recipe: LiveData<RecipeDetails> get() = _recipe

    fun getRecipeDetails(uuid: String) {
        refreshDataFromRepository(uuid)
    }

    private fun refreshDataFromRepository(uuid: String) {
        viewModelScope.launch {
            try {
                refreshRecipeDetailsUseCase.refresh(uuid)
            } catch (networkError: IOException) {
            }
            getRecipeByUuidInteractor.exists(uuid).collect {
                _isExist.value = it
            }
        }
    }

    fun getRecipe(uuid: String) {
        viewModelScope.launch {
            getRecipeByUuidInteractor.get(uuid).collect {
                _recipe.value = it
            }
        }
    }
}