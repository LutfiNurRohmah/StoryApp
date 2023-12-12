package com.lutfi.storyapp.view.favoritestory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lutfi.storyapp.data.UserRepository
import com.lutfi.storyapp.data.database.FavoriteStory

class FavoriteStoryViewModel(private val repository: UserRepository) : ViewModel() {
    fun getAllFavoriteStories(): LiveData<List<FavoriteStory>> = repository.getAllFavoriteStories()
}