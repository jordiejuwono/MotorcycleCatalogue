package com.jordju.core.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MotorcycleOrderDetails(
    val uuid: String = "",
    val motorcycleName: String = "",
    val motorcycleImage: String = "",
    val price: Double = 0.0,
    val orderedBy: String = "",
    val phoneNumber: String = "",
    val addressTo: String = "",
    val paymentMethod: String = "",
    val virtualAccount: String = "",
    val orderedAt: String = "",
    val status: String = "",
): Parcelable