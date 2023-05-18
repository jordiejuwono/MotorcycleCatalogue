package com.jordju.motorcyclecatalogue.ui.authentication.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.jordju.core.data.Resource
import com.jordju.motorcyclecatalogue.R
import com.jordju.motorcyclecatalogue.databinding.ActivityLoginBinding
import com.jordju.motorcyclecatalogue.ui.authentication.register.RegisterActivity
import com.jordju.motorcyclecatalogue.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        bindRegisterText()
        loginUser()
        observeData()
    }

    private fun bindRegisterText() {
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun loginUser() {
        binding.btnLogin.setOnClickListener {
            binding.etEmail.error = null
            binding.etPassword.error = null

            if (!(Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text).matches())) {
                binding.etEmail.error = getString(R.string.text_enter_valid_email)
                binding.etEmail.requestFocus()
            }
            if (binding.etEmail.text.isEmpty()) {
                binding.etEmail.error = getString(R.string.text_email_cannot_empty)
                binding.etEmail.requestFocus()
            }
            if (binding.etPassword.text.length < 4) {
                binding.etPassword.error = getString(R.string.text_password_less_4)
                binding.etEmail.requestFocus()
            }
            if (binding.etPassword.text.isEmpty()) {
                binding.etPassword.error = getString(R.string.text_password_empty)
                binding.etEmail.requestFocus()
            }

            if (binding.etPassword.error == null && binding.etEmail.error == null) {
                viewModel.loginUser(
                    email = binding.etEmail.text.toString(),
                    password = binding.etPassword.text.toString(),
                )
            }
        }
    }

    private fun intentToHomePage() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun showLoading(isVisible: Boolean) {
        binding.flLoading.isVisible = isVisible
    }

    private fun observeData() {
        viewModel.loginState.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    intentToHomePage()
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this, it.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}