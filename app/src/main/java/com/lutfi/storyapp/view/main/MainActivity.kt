package com.lutfi.storyapp.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.lutfi.storyapp.R
import com.lutfi.storyapp.adapter.StoriesAdapter
import com.lutfi.storyapp.data.api.response.ListStoryItem
import com.lutfi.storyapp.databinding.ActivityMainBinding
import com.lutfi.storyapp.view.ViewModelFactory
import com.lutfi.storyapp.view.welcome.WelcomeActivity
import kotlinx.coroutines.launch

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
            } else if (user.isLogin) {
                setupAction()
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_logout) {
            viewModel.logout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupAction() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvStories.layoutManager = layoutManager

        lifecycleScope.launch {
            viewModel.getStories()
        }

        viewModel.listStories.observe(this) {
            setStoriesData(it)
        }
    }

    private fun setStoriesData(stories: List<ListStoryItem?>) {
        val adapter = StoriesAdapter()
        adapter.submitList(stories)
        binding.rvStories.adapter = adapter
    }
}