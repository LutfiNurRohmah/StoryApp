package com.lutfi.storyapp.view.detailstory

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.lutfi.storyapp.R
import com.lutfi.storyapp.data.api.response.Story
import com.lutfi.storyapp.data.database.FavoriteStory
import com.lutfi.storyapp.databinding.ActivityDetailStoryBinding
import com.lutfi.storyapp.view.ViewModelFactory

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    private val viewModel by viewModels<DetailStoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var favoriteStory: FavoriteStory = FavoriteStory()
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    private fun setDetailStory(story: Story) {
        with(binding) {
            titleStoryTextView.text = story.name
            descStoryTextView.text= story.description
            Glide.with(this@DetailStoryActivity)
                .load(story.photoUrl)
                .into(imageStory)
        }
        addFavoriteStory(story)
    }
    private fun addFavoriteStory(story: Story) {
        viewModel.getFavoriteStoryById(story.id).observe(this) { favorite ->
            if (favorite != null) {
                isFavorite = true
                binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                isFavorite = false
                binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
            }
        }

        binding.fabFavorite.setOnClickListener {
            favoriteStory.let { favorite ->
                favorite.createdAt = story.createdAt
                favorite.id = story.id
                favorite.lat = story.lat
                favorite.lon = story.lon
                favorite.name = story.name
                favorite.description = story.description
                favorite.photoUrl= story.photoUrl
            }

            if (isFavorite) {
                isFavorite = false
                viewModel.deleteFavorite(favoriteStory)
                binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
                showToast("Berhasil menghapus dari favorit")
            } else {
                isFavorite = true
                viewModel.insertFavorite(favoriteStory)
                binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
                showToast("Berhasil menambahkan ke favorit")
            }
        }
    }

    companion object {
        private const val ID_STORY = "id_story"
    }
}