package com.jordju.motorcyclecatalogue.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.jordju.motorcyclecatalogue.R
import com.jordju.motorcyclecatalogue.service.MyFirebaseMessagingService
import com.jordju.motorcyclecatalogue.ui.authentication.register.RegisterActivity
import com.jordju.motorcyclecatalogue.ui.home.MainActivity
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

private const val FILENAME = "dd-MMM-yyyy"

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
