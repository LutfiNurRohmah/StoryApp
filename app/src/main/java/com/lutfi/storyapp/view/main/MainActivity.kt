package com.lutfi.storyapp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lutfi.storyapp.R
import com.lutfi.storyapp.databinding.ActivityMainBinding
import com.lutfi.storyapp.view.ViewModelFactory
import com.lutfi.storyapp.view.addstory.AddStoryActivity
import com.lutfi.storyapp.view.favoritestory.FavoriteStoryActivity
import com.lutfi.storyapp.view.maps.MapsActivity
import com.lutfi.storyapp.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                setupAction()
            }
        }

        binding.fabAddStory.setOnClickListener {
            val intent = Intent(this@MainActivity, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_logout) {
            viewModel.logout()
        } else if (item.itemId == R.id.menu_maps) {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        } else if (item.itemId == R.id.menu_favorite) {
            val intent = Intent(this, FavoriteStoryActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupAction() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvStories.layoutManager = layoutManager

        setStoriesData()
    }

    private fun setStoriesData() {
        val adapter = StoriesAdapter()
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        viewModel.story.observe(this) {
            showLoading(true)
            adapter.submitData(lifecycle, it)
            showLoading(false)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}