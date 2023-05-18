package com.jordju.motorcyclecatalogue.ui.checkout

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.type.DateTime
import com.jordju.core.data.Resource
import com.jordju.core.data.local.room.entity.MotorcycleEntity
import com.jordju.core.data.model.MotorcycleOrderDetails
import com.jordju.core.data.model.User
import com.jordju.motorcyclecatalogue.R
import com.jordju.motorcyclecatalogue.databinding.ActivityCheckoutBinding
import com.jordju.motorcyclecatalogue.service.MyFirebaseMessagingService
import com.jordju.motorcyclecatalogue.ui.detail.DetailActivity
import com.jordju.motorcyclecatalogue.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@AndroidEntryPoint
class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding

    private val viewModel: CheckoutViewModel by viewModels()
    private var uid: String = ""
    private var fullName: String = ""
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = "Checkout"
            setDisplayHomeAsUpEnabled(true)
        }

        getData()
        bindData()
        observeData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun getData() {
        viewModel.getUserFullData()
        viewModel.getCurrentUser()
    }

    private fun bindData() {
        val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(CHECKOUT_DATA, MotorcycleEntity::class.java)
        } else {
            intent.getParcelableExtra<MotorcycleEntity>(CHECKOUT_DATA)
        }

        val localeId = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeId)

        binding.apply {
            Glide.with(this@CheckoutActivity)
                .load(userData?.motorcycleImage)
                .into(ivMotorcycle)
            tvName.text = userData?.motorcycleName
            tvPrice.text = formatRupiah.format(userData?.price)
            btnBuy.setOnClickListener {
                // Auto update the user data
                viewModel.saveUserData(
                    uid,
                    User(
                        fullName = fullName,
                        email= email,
                        address = binding.etAddress.text.toString(),
                        phoneNumber = binding.etPhoneNumber.text.toString(),
                    )
                )

                // Send order of motorcycle
                viewModel.sendOrderMotorcycle(
                    uid,
                    MotorcycleOrderDetails(
                        uuid = UUID.randomUUID().toString(),
                        motorcycleName = userData?.motorcycleName ?: "",
                        motorcycleImage = userData?.motorcycleImage ?: "",
                        price = userData?.price ?: 0.0,
                        orderedBy = fullName,
                        phoneNumber = binding.etPhoneNumber.text.toString(),
                        addressTo = binding.etAddress.text.toString(),
                        orderedAt = Calendar.getInstance().time.toString(),
                        status = "In Process"
                    )
                )

            }
        }
    }

    fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView = RemoteViews("com.jordju.motorcyclecatalogue", R.layout.notif_layout)

        remoteView.setTextViewText(R.id.tv_notif_title, title)
        remoteView.setTextViewText(R.id.tv_notif_content, message)
        remoteView.setImageViewResource(R.id.iv_notif_image, R.drawable.ic_motorcycle)

        return remoteView
    }

    fun generateNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val notificationId = System.currentTimeMillis().toInt()

        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, MyFirebaseMessagingService.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_motorcycle)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, message))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    MyFirebaseMessagingService.CHANNEL_ID,
                    MyFirebaseMessagingService.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(notificationId, builder.build())
    }

    private fun observeData() {
        viewModel.userFullDataState.observe(this) {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    binding.etPhoneNumber.setText(it.data?.phoneNumber)
                    binding.etAddress.setText(it.data?.address)
                    fullName = it.data?.fullName ?: ""
                }
                is Resource.Error -> {

                }
            }
        }
        viewModel.currentUserState.observe(this) {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    uid = it.data?.uid ?: ""
                    email = it.data?.email ?: ""
                }
                is Resource.Error -> {

                }
            }
        }

        viewModel.sendOrderState.observe(this) {
            when (it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(CHECKOUT_DATA, MotorcycleEntity::class.java)
                    } else {
                        intent.getParcelableExtra<MotorcycleEntity>(CHECKOUT_DATA)
                    }

                    generateNotification(
                        "Order Received",
                        "Your order for ${userData?.motorcycleName} is now being processed. Thank you for trusting us!"
                    )

                    finish()
                }
                is Resource.Error -> {

                }
            }
        }

    }


    companion object {
        const val CHECKOUT_DATA = "CHECKOUT_DATA"
    }
}