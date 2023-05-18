package com.jordju.motorcyclecatalogue.ui.home.motorcyclelist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jordju.core.data.Resource
import com.jordju.core.domain.entities.Motorcycle
import com.jordju.motorcyclecatalogue.R
import com.jordju.motorcyclecatalogue.databinding.FragmentMotorcycleListBinding
import com.jordju.motorcyclecatalogue.ui.checkout.CheckoutActivity
import com.jordju.motorcyclecatalogue.ui.detail.DetailActivity
import com.jordju.motorcyclecatalogue.ui.favorite.FavoriteActivity
import com.jordju.motorcyclecatalogue.ui.home.motorcyclelist.adapter.MotorcycleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MotorcycleListFragment : Fragment() {

    private val viewModel: MotorcycleListViewModel by viewModels()

    private var _binding: FragmentMotorcycleListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMotorcycleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        intentToFavorite()
        setupRecyclerView()
    }

    private fun getData() {
        viewModel.getAllMotorcycles()
        viewModel.getFcmToken()
    }

    private fun setupRecyclerView() {
        val motorcycleAdapter = MotorcycleAdapter(object : MotorcycleAdapter.OnItemClick {

            override fun onClick(motorcycle: Motorcycle) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.DETAIL_DATA, motorcycle)
                startActivity(intent)
            }

            override fun onCheckOutClick(motorcycle: Motorcycle) {
                val intent = Intent(requireContext(), CheckoutActivity::class.java)
                intent.putExtra(CheckoutActivity.CHECKOUT_DATA, motorcycle)
                startActivity(intent)
            }

            override fun onFavoriteClick(motorcycle: Motorcycle) {
                if (motorcycle.isFavorite) {
                    viewModel.setFavoriteStatus(
                        motorcycleId = motorcycle.motorcycleId,
                        setToFavorite = false,
                    )
                    Toast.makeText(requireContext(), getString(R.string.text_remove_favorite), Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.setFavoriteStatus(
                        motorcycleId = motorcycle.motorcycleId,
                        setToFavorite = true,
                    )
                    Toast.makeText(requireContext(), getString(R.string.text_add_favorite), Toast.LENGTH_SHORT).show()
                }

            }

        })

        with(binding.rvMotorcycles) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = motorcycleAdapter
        }

        observeData(motorcycleAdapter)
    }

    private fun intentToFavorite() {
        binding.ivFavorite.setOnClickListener {
            startActivity(Intent(requireContext(), FavoriteActivity::class.java))
        }
    }

    private fun showLoading(isVisible: Boolean) {
        binding.pbLoading.isVisible = isVisible
    }

    private fun showError(isVisible: Boolean, errorMessage: String? = null) {
        binding.tvErrorMessage.text = errorMessage
        binding.tvErrorMessage.isVisible = isVisible
    }

    private fun observeData(adapter: MotorcycleAdapter) {
        viewModel.motorcyclesState.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showError(false)
                    showLoading(true)
                }
                is Resource.Success -> {
                    showError(false)
                    showLoading(false)
                    adapter.differ.submitList(it.data)
                }
                is Resource.Error -> {
                    showLoading(false)
                    showError(true, it.message.orEmpty())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}