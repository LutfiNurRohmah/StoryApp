package com.lutfi.storyapp.di

import android.content.Context
import com.lutfi.storyapp.data.UserRepository
import com.lutfi.storyapp.data.api.retrofit.ApiConfig
import com.lutfi.storyapp.data.pref.UserPreference
import com.lutfi.storyapp.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig().getApiService()
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref, apiService)
    }
}