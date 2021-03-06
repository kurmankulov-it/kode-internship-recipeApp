package com.example.recipesapp.presentation.ui.recipeslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.recipesapp.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BottomSheetFragment() : BottomSheetDialogFragment() {

    private val viewModel: RecipeListViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding = BottomSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = BottomSheetBinding.bind(view)
        binding.sortByNameLayout.setOnClickListener {
            viewModel.getAllRecipesByName()
            dismiss()
        }
        binding.sortByDateLayout.setOnClickListener {
            viewModel.getAllRecipesByLastUpdate()
            dismiss()
        }
    }
}