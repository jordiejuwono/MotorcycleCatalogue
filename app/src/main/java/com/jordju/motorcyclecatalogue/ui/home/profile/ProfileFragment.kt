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
import com.bumptech.glide.Glide
import com.jordju.core.data.Resource
import com.jordju.motorcyclecatalogue.R
import com.jordju.motorcyclecatalogue.databinding.FragmentMotorcycleListBinding
import com.jordju.motorcyclecatalogue.databinding.FragmentProfileBinding
import com.jordju.motorcyclecatalogue.ui.authentication.login.LoginActivity
import com.jordju.motorcyclecatalogue.ui.editprofile.EditProfileActivity
import com.jordju.motorcyclecatalogue.ui.home.MainActivity
import com.jordju.motorcyclecatalogue.ui.home.motorcyclelist.MotorcycleListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var uid: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editProfileData()
        refreshData()
        getData()
        observeData()
        logoutUser()
    }

    override fun onResume() {
        getData()
        super.onResume()
    }

    private fun refreshData() {
        binding.srlRefresh.setOnRefreshListener {
            getData()
            binding.srlRefresh.isRefreshing = false
        }
    }

    private fun editProfileData() {
        binding.btnEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }
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

    private fun observeData() {
        viewModel.currentUserState.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    uid = data.data?.uid ?: ""
                }
                is Resource.Error -> {

                }
            }
        }

        viewModel.photoState.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if (data.data != null) {
                        Glide.with(requireContext())
                            .load(data.data)
                            .into(binding.ivProfilePicture)
                    }
                }
                is Resource.Error -> {

                }
            }
        }

        viewModel.userFullDataState.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    binding.tvEmail.text = data.data?.email
                    binding.tvFullName.text = data.data?.fullName
                    viewModel.fetchUserPhoto(uid)
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
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_logout_success),
                        Toast.LENGTH_SHORT
                    )
                        .show()
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