package com.jordju.motorcyclecatalogue.ui.editprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.jordju.core.data.Resource
import com.jordju.core.data.model.User
import com.jordju.motorcyclecatalogue.databinding.ActivityEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModels()

    private var userUid: String = ""
    private var emailAddress: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Edit Profile"
            setDisplayHomeAsUpEnabled(true)
        }

        getData()
        observeData()
        editProfileData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun getData() {
        viewModel.getCurrentUser()
        viewModel.getUserFullData()
    }

    private fun editProfileData() {
        binding.btnEditProfile.setOnClickListener {
            if (binding.etFullName.text?.isNotEmpty() == true) {
                viewModel.saveUserData(
                    userUid,
                    User(
                        fullName = binding.etFullName.text.toString(),
                        email = emailAddress,
                        address = binding.etAddress.text.toString(),
                        phoneNumber = binding.etPhoneNumber.text.toString(),
                    )
                )
            } else {
                Toast.makeText(this, "Full Name field cannot be empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeData() {
        viewModel.currentUserState.observe(this) {
            when(it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    userUid = it.data?.uid ?: ""
                    emailAddress = it.data?.email.toString()
                }
                is Resource.Error -> {

                }
            }
        }

        viewModel.saveUserDataState.observe(this) {
            when(it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    Toast.makeText(this, "Update profile success!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Resource.Error -> {

                }
            }
        }

        viewModel.userFullDataState.observe(this) {
            when(it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    binding.etFullName.setText(it.data?.fullName)
                    binding.etAddress.setText(it.data?.address)
                    binding.etPhoneNumber.setText(it.data?.phoneNumber)
                }
                is Resource.Error -> {

                }
            }
        }
    }

}