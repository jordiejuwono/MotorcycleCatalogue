package com.jordju.motorcyclecatalogue.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jordju.motorcyclecatalogue.R
import com.jordju.motorcyclecatalogue.ui.home.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView = RemoteViews("com.jordju.motorcyclecatalogue", R.layout.notif_layout)

        remoteView.setTextViewText(R.id.tv_notif_title, title)
        remoteView.setTextViewText(R.id.tv_notif_content, message)
//        remoteView.setImageViewResource(R.id.iv_notif_image, R.drawable.ic_motorcycle)

        return remoteView
    }

    fun generateNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(applicationContext, CHANNEL_ID)
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
                NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())
    }

    override fun onMessageReceived(message: RemoteMessage) {
        if (message.notification != null) {
            generateNotification(
                message.notification!!.title ?: "",
                message.notification!!.body ?: ""
            )
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    companion object {
        const val CHANNEL_ID = "notification_channel"
        const val CHANNEL_NAME = "com.jordju.motorcyclecatalogue"
    }

}