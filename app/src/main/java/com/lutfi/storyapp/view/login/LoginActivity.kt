package com.lutfi.storyapp.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lutfi.storyapp.data.pref.UserModel
import com.lutfi.storyapp.databinding.ActivityLoginBinding
import com.lutfi.storyapp.view.ViewModelFactory
import com.lutfi.storyapp.view.main.MainActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        loginUser()
    }

    private fun loginUser() {
        viewModel.isLoading.observe(this){
            showLoading(it)
        }

        viewModel.isSuccess.observe(this) {
            showAlert(it)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            lifecycleScope.launch {
                viewModel.login(email, password)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showAlert(isSuccess: Boolean) {
        viewModel.messages.observe(this){ response ->
            val email = binding.emailEditText.text.toString()
            viewModel.token.observe(this) {
                viewModel.saveSession(UserModel(email, it.toString()))
            }
            AlertDialog.Builder(this).apply {
                if (isSuccess) {
                    setTitle("Login Berhasil")
                    setMessage(response)
                    setPositiveButton("Lanjut") { _, _ ->
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                } else if (!isSuccess) {
                    setTitle("Login Gagal")
                    setMessage(response)
                    setNegativeButton("Kembali", null)
                }
                create()
                show()
            }
        }
    }
}