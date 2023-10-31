package com.lutfi.storyapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lutfi.storyapp.data.UserRepository
import com.lutfi.storyapp.data.pref.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}