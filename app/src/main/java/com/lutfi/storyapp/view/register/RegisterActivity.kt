package com.lutfi.storyapp.view.register

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lutfi.storyapp.databinding.ActivityRegisterBinding
import com.lutfi.storyapp.view.ViewModelFactory
import com.lutfi.storyapp.view.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        registerUser()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun registerUser() {
        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        viewModel.isSuccess.observe(this){
            showAlert(it)
        }

        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            lifecycleScope.launch {
                showLoading(true)
                viewModel.registerUser(name, email, password)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showAlert(isSuccess: Boolean) {
        viewModel.messages.observe(this){ response ->
            AlertDialog.Builder(this).apply {
                if (isSuccess) {
                    setTitle("Register Berhasil")
                    setMessage(response)
                    setPositiveButton("Lanjut") { _, _ ->
                        val intent = Intent(context, WelcomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                } else if (!isSuccess) {
                    setTitle("Register Gagal")
                    setMessage(response)
                    setNegativeButton("Kembali", null)
                }
                create()
                show()
            }
        }
    }
}