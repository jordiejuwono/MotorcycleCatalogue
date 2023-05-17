package com.jordju.motorcyclecatalogue.ui.editprofile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.jordju.core.data.Resource
import com.jordju.core.data.model.User
import com.jordju.motorcyclecatalogue.databinding.ActivityEditProfileBinding
import com.jordju.motorcyclecatalogue.utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModels()

    private var userUid: String = ""
    private var emailAddress: String = ""

    private var getFile: File? = null
    private var selectedImage: Uri? = null

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

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) run {
            selectedImage = result.data?.data as Uri
            val myFile = uriToFile(selectedImage!!, this)
            getFile = myFile
            binding.ivProfilePicture.setImageURI(selectedImage)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun editProfileData() {
        binding.btnEditProfile.setOnClickListener {
            // Update profile picture
            if (selectedImage != null) {
                viewModel.saveProfilePicture(userUid, selectedImage!!)
            }

            // Update user data
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
        binding.ivProfilePicture.setOnClickListener {
            startGallery()
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
                    viewModel.fetchUserPhoto(it.data?.uid ?: "")
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

        viewModel.photoState.observe(this) {
            when(it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    if (it.data != null) {
                        Glide.with(this)
                            .load(it.data)
                            .into(binding.ivProfilePicture)
                    }
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