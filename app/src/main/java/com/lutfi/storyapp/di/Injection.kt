package com.lutfi.storyapp.di

import android.content.Context
import com.lutfi.storyapp.data.UserRepository
import com.lutfi.storyapp.data.api.retrofit.ApiConfig
import com.lutfi.storyapp.data.database.StoryDatabase
import com.lutfi.storyapp.data.pref.UserPreference
import com.lutfi.storyapp.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val storyDatabase = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService(context)
        return UserRepository.getInstance(pref, storyDatabase, apiService)
    }
}