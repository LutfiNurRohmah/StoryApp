package com.lutfi.storyapp.data.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lutfi.storyapp.data.api.response.ListStoryItem

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<ListStoryItem>)

    @Query("SELECT * FROM story")
    fun getAllStory(): PagingSource<Int, ListStoryItem>

    @Query("DELETE FROM story")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteStory: FavoriteStory)

    @Delete
    suspend fun delete(favoriteStory: FavoriteStory)

    @Query("SELECT * from favorite_stories")
    fun getAllFavoriteStories(): LiveData<List<FavoriteStory>>

    @Query("SELECT * FROM favorite_stories WHERE id = :id")
    fun getFavoriteStoriesById(id: String): LiveData<FavoriteStory>
}