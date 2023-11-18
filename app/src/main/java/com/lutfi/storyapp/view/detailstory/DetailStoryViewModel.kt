package com.lutfi.storyapp.view.detailstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lutfi.storyapp.data.UserRepository
import com.lutfi.storyapp.data.api.response.Story
import kotlinx.coroutines.launch

class DetailStoryViewModel(private val repository: UserRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _messages = MutableLiveData<String?>()
    val messages: LiveData<String?> = _messages

    private val _detailStory = MutableLiveData<Story?>()
    val detailStory: LiveData<Story?> = _detailStory
    fun getDetail(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            _isLoading.value = false
            val client = repository.getDetailStory(id)
            _detailStory.value = client
        }
    }
}