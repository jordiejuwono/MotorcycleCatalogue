package com.jordju.motorcyclecatalogue.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.jordju.core.data.Resource
import com.jordju.core.data.local.room.entity.MotorcycleEntity
import com.jordju.motorcyclecatalogue.R
import com.jordju.motorcyclecatalogue.databinding.ActivityFavoriteBinding
import com.jordju.motorcyclecatalogue.ui.checkout.CheckoutActivity
import com.jordju.motorcyclecatalogue.ui.detail.DetailActivity
import com.jordju.motorcyclecatalogue.ui.home.motorcyclelist.adapter.FavoriteAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val viewModel: FavoriteViewModel by viewModels()
//    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.text_favorite)
        }

        getData()
        setupRecyclerView()
        observeData()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun getData() {
        viewModel.getAllFavoriteMotorcycles()
    }

    private fun setupRecyclerView() {
        val adapterFavorite = FavoriteAdapter(object : FavoriteAdapter.OnItemClick {

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
                Toast.makeText(this@FavoriteActivity, getString(R.string.text_remove_favorite), Toast.LENGTH_SHORT)
                    .show()
            }

        })

        with(binding.rvFavorite) {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(true)
            adapter = adapterFavorite
        }

        viewModel.favoriteState.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                    binding.tvNoData.visibility = View.GONE
                }
                is Resource.Success -> {
                    showLoading(false)
                    if (it.data?.isEmpty() == true) {
                        binding.tvNoData.visibility = View.VISIBLE
                    }
                    adapterFavorite.differ.submitList(it.data)
                }
                is Resource.Error -> {
                    showLoading(false)
                    binding.tvNoData.visibility = View.GONE
                }
            }
        }

        viewModel.setStateFavorite.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    getData()
                }
                is Resource.Error -> {
                    showLoading(false)
                }
            }
        }

    }

    private fun showLoading(isVisible: Boolean) {
        binding.pbLoading.isVisible = isVisible
    }


    private fun observeData() {

    }
}

