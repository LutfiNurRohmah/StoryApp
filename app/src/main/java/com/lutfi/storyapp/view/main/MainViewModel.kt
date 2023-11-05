package com.lutfi.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lutfi.storyapp.data.UserRepository
import com.lutfi.storyapp.data.api.response.ListStoryItem
import com.lutfi.storyapp.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository): ViewModel() {

    private val _listStories = MutableLiveData<List<ListStoryItem?>>()
    val listStories: LiveData<List<ListStoryItem?>> = _listStories

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
    fun getStories() {
        viewModelScope.launch {
            _listStories.value = repository.getStories()
        }
    }
}