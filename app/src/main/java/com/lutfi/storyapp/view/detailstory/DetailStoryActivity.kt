package com.lutfi.storyapp.view.detailstory

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.lutfi.storyapp.data.api.response.Story
import com.lutfi.storyapp.databinding.ActivityDetailStoryBinding
import com.lutfi.storyapp.view.ViewModelFactory

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    private val viewModel by viewModels<DetailStoryViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val id = intent.getStringExtra(ID_STORY)

        viewModel.getDetail(id.toString())

        viewModel.detailStory.observe(this) {
            setDetailStory(it)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.messages.observe(this) {
            showToast(it)
        }

    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    private fun setDetailStory(story: Story?) {
        with(binding) {
            titleStoryTextView.text = story?.name
            descStoryTextView.text= story?.description
            Glide.with(this@DetailStoryActivity)
                .load(story?.photoUrl)
                .into(imageStory)
        }
    }

    companion object {
        private const val ID_STORY = "id_story"
    }
}