package com.jordju.motorcyclecatalogue.ui.authentication.register

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jordju.core.data.Resource
import com.jordju.core.data.model.User
import com.jordju.motorcyclecatalogue.R
import com.jordju.motorcyclecatalogue.databinding.ActivityRegisterBinding
import com.jordju.motorcyclecatalogue.ui.authentication.login.LoginActivity
import com.jordju.motorcyclecatalogue.utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    private var getFile: File? = null
    private lateinit var currentPhotoPath: String
    private var selectedImage: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        bindLoginText()
        onProfileClick()
        registerUser()
        observeData()
    }

    private fun checkPermissions(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

//    private fun askPermissionForCamera() {
//        if (!allPermissionsGranted()) {
//            ActivityCompat.requestPermissions(
//                this,
//                REQUIRED_PERMISSIONS,
//                REQUEST_CODE_PERMISSIONS
//            )
//        }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission untuk mengakses Camera",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
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
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun bindLoginText() {
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun registerUser() {
        binding.btnRegister.setOnClickListener {
            viewModel.registerUser(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString(),
            )
        }
    }

    private fun saveUserData(dataReference: String, user: User) {
        viewModel.saveUserData(dataReference, user)
    }

    private fun onProfileClick() {
        binding.ivProfilePicture.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = LayoutInflater.from(this).inflate(R.layout.bottom_image_picker, null)

            val btnCamera = view.findViewById<LinearLayout>(R.id.ll_camera)
            val btnGallery = view.findViewById<LinearLayout>(R.id.ll_gallery)

            btnCamera.setOnClickListener {
                Toast.makeText(this, "Camera", Toast.LENGTH_SHORT).show()
            }
            btnGallery.setOnClickListener {
                startGallery()
            }

            dialog.setContentView(view)
            dialog.show()
        }
    }

    private fun observeData() {
        viewModel.registerState.observe(this) {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    saveUserData(
                        it.data?.uid ?: "",
                        User(
                            fullName = binding.etName.text.toString(),
                            email = binding.etEmail.text.toString(),
                        )
                    )
                    if (selectedImage != null) {
                        viewModel.saveProfilePicture(
                            it.data?.uid ?: "",
                            selectedImage!!
                        )
                    }
                    Toast.makeText(this, "Register User Successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                is Resource.Error -> {
                    Toast.makeText(this, it.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}