package com.lutfi.storyapp.view.favoritestory

import androidx.recyclerview.widget.DiffUtil
import com.lutfi.storyapp.data.database.FavoriteStory

class FavStoryDiffCallback(private val oldFavList: List<FavoriteStory>, private val newFavList: List<FavoriteStory>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavList.size

    override fun getNewListSize(): Int = newFavList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavList[oldItemPosition].id == newFavList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavUser = oldFavList[oldItemPosition]
        val newFavUser = newFavList[newItemPosition]
        return oldFavUser.id == newFavUser.id && oldFavUser.photoUrl == newFavUser.photoUrl
    }

}