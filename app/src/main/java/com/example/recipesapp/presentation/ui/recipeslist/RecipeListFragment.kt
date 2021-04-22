package com.example.recipesapp.presentation.ui.recipeslist

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.recipesapp.R
import com.example.recipesapp.databinding.RecipesListFragmentBinding
import com.example.recipesapp.util.Status
import com.example.recipesapp.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private val viewModel: RecipeListViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        val binding = RecipesListFragmentBinding.inflate(inflater, container, false)

        val recipeListAdapter = RecipeListAdapter {
            val action =
                    RecipeListFragmentDirections.actionRecipesListFragmentToRecipeDetailsFragment(it.uuid)
            findNavController().navigate(action)
        }

        binding.recyclerViewRecipeList.adapter = recipeListAdapter

        binding.noInternetDialog.noInternetBtn.setOnClickListener {
            viewModel.refresh()
        }

        viewModel.recipes.observe(viewLifecycleOwner, { recipes ->
            when (recipes.status) {
                Status.LOADING -> {
                    binding.recipesListProgressBar.visible(true)
                    binding.noInternetDialog.noInternetLayout.visible(false)
                }

                Status.SUCCESS -> {
                    binding.recyclerViewRecipeList.visible(true)
                    binding.recipesListProgressBar.visible(false)
                    if (recipes.data != null) {
                        recipeListAdapter.recipes = recipes.data
                    }
                }

                Status.ERROR -> {
                    binding.noInternetDialog.noInternetLayout.visible(true)
                    binding.recipesListProgressBar.visible(false)
                    Toast.makeText(requireContext(), R.string.no_internet_message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipe_list_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    viewModel.getRecipesWithSearch(newText)
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                if (!query.isNullOrEmpty()) {
                    viewModel.getRecipesWithSearch(query)
                }
                return false
            }
        })

        searchView.setOnCloseListener {
            viewModel.getAllRecipes()
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sorts -> {
                val bottomSheetFragment = BottomSheetFragment()
                bottomSheetFragment.show(requireActivity().supportFragmentManager, "BottomSheet")
            }
        }
        return true
    }
}