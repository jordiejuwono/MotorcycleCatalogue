package com.jordju.motorcyclecatalogue.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.jordju.core.data.Resource
import com.jordju.motorcyclecatalogue.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerUser()
        observeData()
    }

    private fun registerUser() {
        binding.btnLogin.setOnClickListener {
            viewModel.registerUser(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString(),
            )
        }
    }

    private fun observeData() {
        viewModel.loginState.observe(this) {
            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {

                }
            }
        }
    }
}