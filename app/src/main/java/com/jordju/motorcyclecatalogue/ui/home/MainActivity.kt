package com.jordju.motorcyclecatalogue.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jordju.motorcyclecatalogue.R
import com.jordju.motorcyclecatalogue.databinding.ActivityMainBinding
import com.jordju.motorcyclecatalogue.ui.home.motorcyclelist.MotorcycleListFragment
import com.jordju.motorcyclecatalogue.ui.home.profile.ProfileFragment
import com.jordju.motorcyclecatalogue.ui.home.transaction.TransactionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceContainerFragment(MotorcycleListFragment())
        }

        binding.bnvBottomNav.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.menu_home -> {
                    replaceContainerFragment(MotorcycleListFragment())
                }
                R.id.menu_favorite -> {
                    replaceContainerFragment(TransactionFragment())
                }
                R.id.menu_profile -> {
                    replaceContainerFragment(ProfileFragment())
                }
            }
            return@setOnItemSelectedListener true
        }

    }

    private fun replaceContainerFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_container, fragment)
            .commit()
    }
}