package com.example.recipesapp.presentation.ui.recipeslist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipesapp.databinding.RecipeItemBinding
import com.example.recipesapp.domain.model.Recipe
import com.example.recipesapp.util.DATE_PATTERN
import java.text.SimpleDateFormat
import java.util.*

class RecipeListAdapter(private val recipeClickCallBack: (Recipe) -> Unit) :
        RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>() {
    var recipes: List<Recipe> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        val recipe = recipes[position]

        holder.itemView.setOnClickListener {
            recipeClickCallBack(recipe)
        }

        holder.bind(recipe)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        return RecipeListViewHolder.from(parent)
    }

    class RecipeListViewHolder(viewBinding: RecipeItemBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {
        private val recipeName: TextView = viewBinding.recipeName
        private val recipeDescription: TextView = viewBinding.recipeDescription
        private val recipeLastUpdate: TextView = viewBinding.recipeLastUpdate
        private val recipeImage: ImageView = viewBinding.recipeImage

        fun bind(item: Recipe) {
            recipeName.text = item.name
            recipeDescription.text = item.description
            recipeLastUpdate.text = SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(item.lastUpdated)
            Glide.with(itemView)
                    .load(item.images?.first())
                    .into(recipeImage)
        }

        companion object {
            fun from(parent: ViewGroup): RecipeListViewHolder {
                val view: RecipeItemBinding = RecipeItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )

                return RecipeListViewHolder(view)
            }
        }
    }
}


