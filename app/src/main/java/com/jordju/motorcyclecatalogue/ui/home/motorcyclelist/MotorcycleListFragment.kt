package com.jordju.motorcyclecatalogue.ui.home.motorcyclelist

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
import com.jordju.core.data.local.entity.MotorcycleEntity
import com.jordju.motorcyclecatalogue.databinding.FragmentMotorcycleListBinding
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
        setupRecyclerView()
    }

    private fun getData() {
        viewModel.getAllMotorcycles()
    }

    private fun setupRecyclerView() {
        val motorcycleAdapter = MotorcycleAdapter(object : MotorcycleAdapter.OnItemClick {

            override fun onClick(motorcycle: MotorcycleEntity) {
                Toast.makeText(requireContext(), motorcycle.motorcycleName, Toast.LENGTH_SHORT).show()
            }

        })

        with(binding.rvMotorcycles) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = motorcycleAdapter
        }

        observeData(motorcycleAdapter)
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