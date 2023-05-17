package com.jordju.motorcyclecatalogue.ui.home.transaction

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jordju.core.data.Resource
import com.jordju.core.data.local.entity.MotorcycleEntity
import com.jordju.core.data.model.MotorcycleOrderDetails
import com.jordju.motorcyclecatalogue.databinding.FragmentTransactionBinding
import com.jordju.motorcyclecatalogue.ui.home.transaction.adapter.TransactionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        setupRecyclerView()
    }

    private fun getData() {
        viewModel.getCurrentUser()
    }

    private fun setupRecyclerView() {
        val transactionAdapter = TransactionAdapter(object : TransactionAdapter.OnItemClick {

            override fun onClick(motorcycle: MotorcycleOrderDetails) {
//                val intent = Intent(requireContext(), DetailActivity::class.java)
//                intent.putExtra(DetailActivity.DETAIL_DATA, motorcycle)
//                startActivity(intent)
            }


        })

        with(binding.rvTransactions) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = transactionAdapter
        }

        observeData(transactionAdapter)

    }

    private fun showLoading(isVisible: Boolean) {
        binding.pbLoading.isVisible = isVisible
    }

    private fun observeData(adapter: TransactionAdapter) {
        viewModel.currentUserState.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    viewModel.getMotorcyclesOrder(it.data?.uid ?: "")
                }
                is Resource.Error -> {

                }
            }
        }

        viewModel.transactionListState.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    adapter.differ.submitList(it.data)
                }
                is Resource.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}