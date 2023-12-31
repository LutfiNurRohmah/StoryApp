package com.lutfi.storyapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.lutfi.storyapp.data.api.response.ErrorResponse
import com.lutfi.storyapp.data.api.response.ListStoryItem
import com.lutfi.storyapp.data.api.response.LoginResponse
import com.lutfi.storyapp.data.api.response.Story
import com.lutfi.storyapp.data.api.retrofit.ApiService
import com.lutfi.storyapp.data.database.FavoriteStory
import com.lutfi.storyapp.data.database.StoryDatabase
import com.lutfi.storyapp.data.pref.UserModel
import com.lutfi.storyapp.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService,
){
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun registerUser(name: String, email: String, password: String) : String {
        return apiService.register(name, email, password).message.toString()
    }

    suspend fun loginUser(email: String, password: String) : LoginResponse {
        return apiService.login(email, password)
    }

    fun getStories() : LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
            pagingSourceFactory = {
//                StoriesPagingSource(apiService)
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }
    suspend fun getDetailStory(id: String) : Story {
        val response = apiService.getDetailStory(id).story
        Log.d("apii", "$response")
        return response
    }

    suspend fun addStory(file: MultipartBody.Part, desc: RequestBody): ErrorResponse{
        return apiService.addStory(file, desc)
    }

    suspend fun getStoriesWithLocation() : List<ListStoryItem> {
        return apiService.getStoriesWithLocation().listStory
    }

    fun getAllFavoriteStories(): LiveData<List<FavoriteStory>> = storyDatabase.storyDao().getAllFavoriteStories()

    fun getFavoriteStoryById(id: String): LiveData<FavoriteStory> = storyDatabase.storyDao().getFavoriteStoriesById(id)

    suspend fun insertFavorite(favoriteStory: FavoriteStory) {
        storyDatabase.storyDao().insert(favoriteStory)
    }

    suspend fun deleteFavorite(favoriteStory: FavoriteStory) {
        storyDatabase.storyDao().delete(favoriteStory)
    }


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            storyDatabase: StoryDatabase,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, storyDatabase, apiService)
            }.also { instance = it }
    }
}