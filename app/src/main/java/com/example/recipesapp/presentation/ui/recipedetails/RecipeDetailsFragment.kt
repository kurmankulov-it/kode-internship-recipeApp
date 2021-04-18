package com.example.recipesapp.presentation.ui.recipedetails

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.recipesapp.databinding.RecipeDetailsFragmentBinding
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

    private var _binding: RecipeDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = RecipeDetailsFragmentBinding.inflate(inflater, container, false)

        val recipeBriefAdapter = RecipeDetailsAdapter {
            val action = RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentSelf(it.uuid)
            findNavController().navigate(action)
        }
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recipeDetailsBrief.adapter = recipeBriefAdapter
        binding.recipeDetailsBrief.layoutManager = mLayoutManager

        viewModel.isExist.observe(viewLifecycleOwner, {
            if (it)
            {
                viewModel.getRecipe(args.recipeUuid)
                binding.recipeDetailsProgressBar.visibility = View.GONE
            }
        })

        viewModel.recipe.observe(viewLifecycleOwner, {
            it?.let {
                binding.recipeDetailImages.setImageList(loadImagesToSlider(it.images!!))
                binding.recipeDetailImages.setItemClickListener(object : ItemClickListener {
                    override fun onItemSelected(position: Int) {
                        val action = RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToRecipeImageDownload(it.images[position])
                        findNavController().navigate(action)
                    }
                })
                binding.recipeDetailName.text = it.name
                binding.recipeDetailLastUpdate.text = SimpleDateFormat("DD.MM.yyyy", Locale.getDefault()).format(it.lastUpdated)
                binding.recipeDetailDescription.text = it.description
                binding.recipeDetailDifficulty.rating = it.difficulty.toFloat()
                binding.recipeDetailInstruction.text = Html.fromHtml(it.instructions)
                recipeBriefAdapter.briefs = it.similar
            }
        })

        viewModel.getRecipeDetails(args.recipeUuid)
        return binding.root
    }

    private fun loadImagesToSlider(images: List<String>): List<SlideModel>
    {
        val slideModels = ArrayList<SlideModel>()
        images.forEach {
            slideModels.add(SlideModel(it, ScaleTypes.CENTER_CROP))
        }

        return slideModels
    }
}