package com.example.recipesapp.presentation.ui.recipedetails

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.recipesapp.R
import com.example.recipesapp.databinding.RecipeDetailsFragmentBinding
import com.example.recipesapp.domain.model.RecipeDetails
import com.example.recipesapp.util.DATE_PATTERN
import com.example.recipesapp.util.Status
import com.example.recipesapp.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeDetailsFragment : Fragment() {

    private val args by navArgs<RecipeDetailsFragmentArgs>()

    private val viewModel: RecipeDetailsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = RecipeDetailsFragmentBinding.inflate(inflater, container, false)

        val recipeBriefAdapter = RecipeDetailsAdapter {
            val action = RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentSelf(it.uuid)
            findNavController().navigate(action)
        }

        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recipeDetailsBrief.adapter = recipeBriefAdapter
        binding.recipeDetailsBrief.layoutManager = mLayoutManager

        binding.noInternetDialog.noInternetBtn.setOnClickListener {
            viewModel.getRecipeDetails(args.recipeUuid)
        }

        viewModel.recipe.observe(viewLifecycleOwner, { recipe ->
            when (recipe.status) {
                Status.LOADING -> {
                    binding.recipeDetailsView.visible(false)
                    binding.recipeDetailsProgressBar.visible(true)
                    binding.noInternetDialog.noInternetLayout.visible(false)
                }

                Status.SUCCESS -> {
                    binding.recipeDetailsProgressBar.visible(false)
                    binding.recipeDetailsView.visible(true)
                    binding.noInternetDialog.noInternetLayout.visible(false)
                    if (recipe.data != null) {
                        bindRecipeDetails(recipe.data)
                        recipeBriefAdapter.briefs = recipe.data.similar
                    }
                }

                Status.ERROR -> {
                    binding.noInternetDialog.noInternetLayout.visible(true)
                    binding.recipeDetailsProgressBar.visible(false)
                    binding.recipeDetailsView.visible(false)
                    Toast.makeText(requireContext(), R.string.no_internet_message, Toast.LENGTH_SHORT).show()
                }
            }

        })

        viewModel.getRecipeDetails(args.recipeUuid)
        return binding.root
    }

    private fun loadImagesToSlider(images: List<String>): List<SlideModel> {
        val slideModels = ArrayList<SlideModel>()
        images.forEach {
            slideModels.add(SlideModel(it, ScaleTypes.CENTER_CROP))
        }

        return slideModels
    }

    private fun bindRecipeDetails(recipe: RecipeDetails) {
        val binding = RecipeDetailsFragmentBinding.bind(requireView())
        recipe.let {
            binding.recipeDetailImages.setImageList(loadImagesToSlider(it.images))
            binding.recipeDetailImages.setItemClickListener(object : ItemClickListener {
                override fun onItemSelected(position: Int) {
                    val action = RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToRecipeImageDownload(it.images[position])
                    findNavController().navigate(action)
                }
            })
            binding.recipeDetailName.text = it.name
            binding.recipeDetailLastUpdate.text = SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(it.lastUpdated)
            binding.recipeDetailDescription.text = it.description
            binding.recipeDetailDifficulty.rating = it.difficulty.toFloat()
            binding.recipeDetailInstruction.text = Html.fromHtml(it.instructions)
        }
    }
}