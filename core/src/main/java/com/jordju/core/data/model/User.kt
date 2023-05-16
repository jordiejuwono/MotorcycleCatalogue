package com.jordju.core.data.model

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val fullName: String = "",
    val email: String = "",
    val address: String = "",
    val phoneNumber: String = ""
): Parcelable