package com.jordju.motorcyclecatalogue.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.jordju.core.data.Resource
import com.jordju.core.data.local.room.entity.MotorcycleEntity
import com.jordju.motorcyclecatalogue.databinding.ActivityFavoriteBinding
import com.jordju.motorcyclecatalogue.ui.checkout.CheckoutActivity
import com.jordju.motorcyclecatalogue.ui.detail.DetailActivity
import com.jordju.motorcyclecatalogue.ui.home.motorcyclelist.adapter.MotorcycleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: MotorcycleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Favorite"
        }

        getData()
        setupRecyclerView()
    }

    override fun onNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onNavigateUp()
    }
    private fun getData() {
        viewModel.getAllFavoriteMotorcycles()
    }

    private fun setupRecyclerView() {
        adapter = MotorcycleAdapter(object : MotorcycleAdapter.OnItemClick {

            override fun onClick(motorcycle: MotorcycleEntity) {
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.DETAIL_DATA, motorcycle)
                startActivity(intent)
            }

            override fun onCheckOutClick(motorcycle: MotorcycleEntity) {
                val intent = Intent(this@FavoriteActivity, CheckoutActivity::class.java)
                intent.putExtra(CheckoutActivity.CHECKOUT_DATA, motorcycle)
                startActivity(intent)
            }

            override fun onFavoriteClick(motorcycle: MotorcycleEntity) {
                viewModel.setFavoriteStatus(
                    motorcycleId = motorcycle.motorcycleId,
                    setToFavorite = false,
                )
                Toast.makeText(this@FavoriteActivity, "Removed from Favorite!", Toast.LENGTH_SHORT)
                    .show()
            }

        })

        with(binding.rvFavorite) {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapter = this@FavoriteActivity.adapter
        }

        observeData(adapter)
    }

    private fun showLoading(isVisible: Boolean) {
        binding.pbLoading.isVisible = isVisible
    }

    private fun showNoData(isMessageVisible: Boolean) {
        binding.tvNoData.isVisible = isMessageVisible
        binding.tvNoData.text = "Favorite List\nis Empty"
    }

    private fun observeData(adapter: MotorcycleAdapter) {
        viewModel.favoriteState.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showNoData(false)
                    showLoading(true)
                }
                is Resource.Success -> {
                    showNoData(false)
                    showLoading(false)
                    adapter.differ.submitList(it.data)
                }
                is Resource.Error -> {
                    showLoading(false)
                    showNoData(false)
                }
            }
        }
    }
}