package com.lutfi.storyapp.data

import com.lutfi.storyapp.data.api.response.ListStoryItem
import com.lutfi.storyapp.data.api.response.LoginResponse
import com.lutfi.storyapp.data.api.retrofit.ApiService
import com.lutfi.storyapp.data.pref.UserModel
import com.lutfi.storyapp.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
){
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun registerUser(name: String, email: String, password: String) : String? {
        return apiService.register(name, email, password).message.toString()
    }

    suspend fun loginUser(email: String, password: String) : LoginResponse {
        return apiService.login(email, password)
    }

    suspend fun getStories() : List<ListStoryItem> {
        return apiService.getStories().listStory
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}