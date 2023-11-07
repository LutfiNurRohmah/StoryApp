package com.lutfi.storyapp.view.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lutfi.storyapp.R
import com.lutfi.storyapp.data.api.response.ListStoryItem
import com.lutfi.storyapp.databinding.ItemStoriesBinding
import com.lutfi.storyapp.view.detailstory.DetailStoryActivity

class StoriesAdapter : ListAdapter<ListStoryItem, StoriesAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val stories = getItem(position)
        var imgPhoto: ImageView = holder.itemView.findViewById(R.id.imageStory)
        var tvName: TextView = holder.itemView.findViewById(R.id.titleStoryTextView)
        var tvDescription: TextView = holder.itemView.findViewById(R.id.descStoryTextView)

        holder.bind(stories)

        val id = stories.id

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailStoryActivity::class.java)
            intentDetail.putExtra("id_story", id)

            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(imgPhoto, "image"),
                    Pair(tvName, "name"),
                    Pair(tvDescription, "description"),
                )
            holder.itemView.context.startActivity(intentDetail, optionsCompat.toBundle())
        }
    }

    class MyViewHolder(val binding: ItemStoriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(stories: ListStoryItem) {
            with(binding) {
                titleStoryTextView.text = stories.name
                descStoryTextView.text= stories.description
                Glide.with(itemView.context)
                    .load(stories.photoUrl)
                    .into(imageStory)
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}