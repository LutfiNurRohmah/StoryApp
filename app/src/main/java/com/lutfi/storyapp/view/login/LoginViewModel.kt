package com.lutfi.storyapp.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.lutfi.storyapp.data.UserRepository
import com.lutfi.storyapp.data.api.response.ErrorResponse
import com.lutfi.storyapp.data.pref.UserModel
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _messages = MutableLiveData<String?>()
    val messages: LiveData<String?> = _messages

    private val _token = MutableLiveData<String?>()
    val token: LiveData<String?> = _token

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    suspend fun login(email: String, password: String) {
        _isLoading.value = true
        try {
            val response = repository.loginUser(email, password)
            _isLoading.value = false
            _token.value = response.loginResult?.token
            _isSuccess.value = true
            _messages.value = response.message
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            _isLoading.value = false
            _isSuccess.value = false
            _messages.value = errorMessage.toString()
        }
    }
}