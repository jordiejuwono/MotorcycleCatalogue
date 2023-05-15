package com.jordju.core.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val fullName: String,
    val email: String,
    val age: Int
): Parcelable