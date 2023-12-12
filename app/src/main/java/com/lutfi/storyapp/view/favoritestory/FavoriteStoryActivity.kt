package com.lutfi.storyapp.view.favoritestory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lutfi.storyapp.databinding.ActivityFavoriteStoryBinding
import com.lutfi.storyapp.view.ViewModelFactory

class FavoriteStoryActivity : AppCompatActivity() {

    private var _activityFavoriteStory: ActivityFavoriteStoryBinding? = null
    private val binding get() = _activityFavoriteStory

    private lateinit var adapter: FavoriteStoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteStory = ActivityFavoriteStoryBinding.inflate(layoutInflater)
        setContentView(_activityFavoriteStory?.root)

        val favoriteStoryViewModel = obtainViewModel(this@FavoriteStoryActivity)
        favoriteStoryViewModel.getAllFavoriteStories().observe(this) { favStories ->
            if (favStories != null) {
                adapter.setListFavStory(favStories)
            }
        }

        adapter = FavoriteStoryAdapter()

        binding?.rvStory?.layoutManager = LinearLayoutManager(this)
        binding?.rvStory?.setHasFixedSize(true)
        binding?.rvStory?.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity) : FavoriteStoryViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteStoryViewModel::class.java]
    }
}