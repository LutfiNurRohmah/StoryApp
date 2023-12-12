package com.lutfi.storyapp.view.favoritestory

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lutfi.storyapp.data.database.FavoriteStory
import com.lutfi.storyapp.databinding.ItemStoriesBinding
import com.lutfi.storyapp.view.detailstory.DetailStoryActivity

class FavoriteStoryAdapter: RecyclerView.Adapter<FavoriteStoryAdapter.FavoriteStoryViewHolder>() {

    private val listFavStory = ArrayList<FavoriteStory>()

    fun setListFavStory(listFavStory: List<FavoriteStory>) {
        val diffCallback = FavStoryDiffCallback(this.listFavStory, listFavStory)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavStory.clear()
        this.listFavStory.addAll(listFavStory)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteStoryAdapter.FavoriteStoryViewHolder {
        val binding = ItemStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteStoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FavoriteStoryAdapter.FavoriteStoryViewHolder,
        position: Int
    ) {
        val story = listFavStory[position]
        holder.bind(story)
        val id = story.id

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailStoryActivity::class.java)
            intentDetail.putExtra("id_story", id)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listFavStory.size

    inner class FavoriteStoryViewHolder(private val binding: ItemStoriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteStory: FavoriteStory) {
            with(binding) {
                titleStoryTextView.text = favoriteStory.name
                descStoryTextView.text= favoriteStory.description
                Glide.with(itemView.context)
                    .load(favoriteStory.photoUrl)
                    .into(imageStory)
            }
        }
    }
}