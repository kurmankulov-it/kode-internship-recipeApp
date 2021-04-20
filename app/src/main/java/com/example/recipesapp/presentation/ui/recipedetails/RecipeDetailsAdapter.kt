package com.example.recipesapp.presentation.ui.recipedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipesapp.data.database.entities.RecipeBrief
import com.example.recipesapp.databinding.RecipeDetailsBriefItemBinding

class RecipeDetailsAdapter(private val recipeBriefClickCallBack: (RecipeBrief) -> Unit) :
        RecyclerView.Adapter<RecipeDetailsAdapter.RecipeDetailsViewHolder>() {

    var briefs: List<RecipeBrief> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return briefs.size
    }

    override fun onBindViewHolder(holder: RecipeDetailsViewHolder, position: Int) {
        val brief = briefs[position]

        holder.itemView.setOnClickListener {
            recipeBriefClickCallBack(brief)
        }

        holder.bind(brief)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDetailsViewHolder {

        return RecipeDetailsViewHolder.from(parent)
    }

    class RecipeDetailsViewHolder(viewBinding: RecipeDetailsBriefItemBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {
        private val recipeBriefName: TextView = viewBinding.recipeBriefName
        private val recipeBriefImage: ImageView = viewBinding.recipeBriefImage

        fun bind(brief: RecipeBrief) {
            recipeBriefName.text = brief.name
            Glide.with(itemView)
                    .load(brief.image)
                    .into(recipeBriefImage)
        }

        companion object {
            fun from(parent: ViewGroup): RecipeDetailsViewHolder {
                val view: RecipeDetailsBriefItemBinding = RecipeDetailsBriefItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
                return RecipeDetailsViewHolder(view)
            }
        }
    }
}