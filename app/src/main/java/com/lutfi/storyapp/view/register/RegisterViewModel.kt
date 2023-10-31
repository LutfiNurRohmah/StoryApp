package com.lutfi.storyapp.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.lutfi.storyapp.data.UserRepository
import com.lutfi.storyapp.data.api.response.ErrorResponse
import retrofit2.HttpException


class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _messages = MutableLiveData<String>()
    val messages: LiveData<String> = _messages

    suspend fun registerUser(name: String, email: String, password: String) {
        try {
            val message = userRepository.registerUser(name, email, password)
            _isLoading.value = false
            _messages.value = message.toString()
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            _isLoading.value = false
            _messages.value = errorMessage.toString()
        }
    }
}