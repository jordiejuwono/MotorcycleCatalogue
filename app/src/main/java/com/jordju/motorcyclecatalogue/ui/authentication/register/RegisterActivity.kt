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
        private const val FILENAME = "dd-MMM-yyyy"
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

    private fun askPermissionForCamera() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

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


    val getTimeStamp = SimpleDateFormat(
        FILENAME,
        Locale.US
    ).format(System.currentTimeMillis())

    fun createCustomTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(getTimeStamp, ".jpg", storageDir)
    }

    fun uriToFile(selectedImage: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImage) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
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
            viewModel.saveUserData(
                User(
                    fullName = binding.etName.text.toString(),
                    email = binding.etEmail.text.toString(),
                    age = 27,
                )
            )
            if (selectedImage != null) {
                viewModel.saveProfilePicture(
                    selectedImage!!
                )
            }
        }
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
                    Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    Toast.makeText(this, it.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}