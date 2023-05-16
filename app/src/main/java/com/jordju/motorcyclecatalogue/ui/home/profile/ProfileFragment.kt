package com.jordju.motorcyclecatalogue.ui.home.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.jordju.core.data.Resource
import com.jordju.motorcyclecatalogue.R
import com.jordju.motorcyclecatalogue.databinding.FragmentMotorcycleListBinding
import com.jordju.motorcyclecatalogue.databinding.FragmentProfileBinding
import com.jordju.motorcyclecatalogue.ui.authentication.login.LoginActivity
import com.jordju.motorcyclecatalogue.ui.home.MainActivity
import com.jordju.motorcyclecatalogue.ui.home.motorcyclelist.MotorcycleListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getData()
        logoutUser()
        bindData()
    }

    private fun getData() {
        viewModel.getCurrentUser()
        viewModel.getUserFullData()
    }

    private fun logoutUser() {
        binding.btnLogout.setOnClickListener {
            viewModel.logoutUser()
        }
    }

    private fun showLoading(isVisible: Boolean) {
        binding.clContent.isVisible = !isVisible
        binding.pbLoading.isVisible = isVisible
    }

    private fun bindData() {
        viewModel.userFullDataState.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    binding.tvEmail.text = data.data?.email
                    binding.tvFullName.text = data.data?.fullName
                }
                is Resource.Error -> {
                    showLoading(false)
                }
            }
        }

        viewModel.logoutState.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Logout Successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                is Resource.Error -> {

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}