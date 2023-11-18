package com.lutfi.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.lutfi.storyapp.data.UserRepository
import com.lutfi.storyapp.data.api.response.ListStoryItem
import com.lutfi.storyapp.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _messages = MutableLiveData<String?>()
    val messages: LiveData<String?> = _messages

//    private val _listStories = MutableLiveData<List<ListStoryItem?>>()
//    val listStories: LiveData<List<ListStoryItem?>> = _listStories

    val story: LiveData<PagingData<ListStoryItem>> =
        repository.getStories().cachedIn(viewModelScope)

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
//    fun getStories() {
//        _isLoading.value = true
//        viewModelScope.launch {
//            try {
//                _listStories.value = repository.getStories()
//                _isLoading.value = false
//            } catch (e: HttpException) {
//                val jsonInString = e.response()?.errorBody()?.string()
//                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
//                val errorMessage = errorBody.message
//                _messages.value = errorMessage.toString()
//                _isLoading.value = false
//            }
//        }
//    }
}