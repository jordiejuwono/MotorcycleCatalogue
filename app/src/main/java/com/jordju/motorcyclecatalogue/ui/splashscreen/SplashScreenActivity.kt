package com.jordju.motorcyclecatalogue.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jordju.motorcyclecatalogue.databinding.ActivitySplashScreenBinding
import com.jordju.motorcyclecatalogue.ui.authentication.login.LoginActivity
import com.jordju.motorcyclecatalogue.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding
    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        lifecycleScope.launch(Dispatchers.IO) {
            delay(DELAY_TIME)
            if (viewModel.isUserLoggedIn()) {
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            }
        }
    }

    companion object {
        const val DELAY_TIME = 2500L
    }
}