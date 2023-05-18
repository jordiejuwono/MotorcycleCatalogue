package com.jordju.motorcyclecatalogue.ui.orderdetail

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.jordju.core.data.Resource
import com.jordju.core.data.model.MotorcycleOrderDetails
import com.jordju.motorcyclecatalogue.R
import com.jordju.motorcyclecatalogue.databinding.ActivityOrderDetailBinding
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
            tvPaymentType.text = userData?.paymentMethod
            btnCancel.setOnClickListener {
                viewModel.cancelOrder(
                    uid,
                    orderId = userData?.uuid ?: ""
                )
            }
        }
    }

    private fun generateNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        }

        val notificationId = System.currentTimeMillis().toInt()

        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_motorcycle)
                .setContentTitle(title)
                .setStyle(NotificationCompat.BigTextStyle()
                    .setBigContentTitle(title)
                    .setSummaryText(title)
                    .bigText(message))
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(notificationId, builder.build())
    }

    private fun showLoading(isVisible: Boolean) {
        binding.apply {
            svContent.isVisible = !isVisible
            pbLoading.isVisible = isVisible
        }
    }

    private fun showSendLoading(isVisible: Boolean) {
        binding.flLoading.isVisible = isVisible
    }

    private fun observeData() {
        viewModel.currentUserState.observe(this) {
            when(it) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    uid = it.data?.uid ?: ""
                    showLoading(false)
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this, it.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.cancelOrderState.observe(this) {
            when(it) {
                is Resource.Loading -> {
                    showSendLoading(true)
                }
                is Resource.Success -> {
                    showSendLoading(false)
                    val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(ORDER_DETAIL, MotorcycleOrderDetails::class.java)
                    } else {
                        intent.getParcelableExtra<MotorcycleOrderDetails>(ORDER_DETAIL)
                    }
                    
                    generateNotification(
                        title = getString(R.string.text_order_cancel),
                        message = "Your order for ${userData?.motorcycleName} has been canceled",
                    )

                    finish()
                }
                is Resource.Error -> {
                    showLoading(false)
                    Toast.makeText(this, getString(R.string.text_cancel_order_failed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    companion object {
        const val ORDER_DETAIL = "ORDER_DETAIL"
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "notification channel"
    }
}