package com.jordju.motorcyclecatalogue.ui.orderdetail

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.jordju.core.data.Resource
import com.jordju.core.data.local.entity.MotorcycleEntity
import com.jordju.core.data.model.MotorcycleOrderDetails
import com.jordju.motorcyclecatalogue.R
import com.jordju.motorcyclecatalogue.databinding.ActivityOrderDetailBinding
import com.jordju.motorcyclecatalogue.service.MyFirebaseMessagingService
import com.jordju.motorcyclecatalogue.ui.detail.DetailActivity
import com.jordju.motorcyclecatalogue.ui.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding

    private val viewModel: OrderDetailViewModel by viewModels()
    private var uid: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getData()
        bindData()
        observeData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun getData() {
        viewModel.getCurrentUser()
    }

    private fun bindData() {
        val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(ORDER_DETAIL, MotorcycleOrderDetails::class.java)
        } else {
            intent.getParcelableExtra<MotorcycleOrderDetails>(ORDER_DETAIL)
        }

        supportActionBar?.title = userData?.motorcycleName

        val localeId = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeId)

        binding.apply {
            Glide.with(this@OrderDetailActivity)
                .load(userData?.motorcycleImage)
                .into(ivMotorcycle)
            tvName.text = userData?.motorcycleName
            tvPrice.text = formatRupiah.format(userData?.price)
            tvOrderId.text = userData?.uuid
            tvPhoneNumber.text = userData?.phoneNumber
            tvAddress.text = userData?.addressTo
            tvStatus.text = userData?.status
            tvOrderedBy.text = userData?.orderedBy
            btnCancel.setOnClickListener {
                viewModel.cancelOrder(
                    uid,
                    orderId = userData?.uuid ?: ""
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
        viewModel.currentUserState.observe(this) {
            when(it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    uid = it.data?.uid ?: ""
                }
                is Resource.Error -> {

                }
            }
        }

        viewModel.cancelOrderState.observe(this) {
            when(it) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(ORDER_DETAIL, MotorcycleOrderDetails::class.java)
                    } else {
                        intent.getParcelableExtra<MotorcycleOrderDetails>(ORDER_DETAIL)
                    }

                    generateNotification(
                        title = "Order Canceled",
                        message = "Your order for ${userData?.motorcycleName} has been canceled",
                    )

                    finish()
                }
                is Resource.Error -> {

                }
            }
        }
    }


    companion object {
        const val ORDER_DETAIL = "ORDER_DETAIL"
    }
}