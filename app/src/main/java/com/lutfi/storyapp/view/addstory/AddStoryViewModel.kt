package com.lutfi.storyapp.view.addstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.lutfi.storyapp.data.UserRepository
import com.lutfi.storyapp.data.api.response.ErrorResponse
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class AddStoryViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _messages = MutableLiveData<String?>()
    val messages: LiveData<String?> = _messages

    fun addStory(multipartBody: MultipartBody.Part, requestBody: RequestBody) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val successResponse = userRepository.addStory(multipartBody, requestBody)
                _messages.value = successResponse.message
                _isLoading.value = false
                _isSuccess.value = true
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                _messages.value = errorResponse.message
                _isLoading.value = false
                _isSuccess.value = false
            }
        }
    }
}