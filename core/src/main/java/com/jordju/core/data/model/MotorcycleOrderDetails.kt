package com.jordju.core.data.model

data class MotorcycleOrderDetails(
    val uuid: String = "",
    val motorcycleName: String = "",
    val motorcycleImage: String = "",
    val price: Double = 0.0,
    val phoneNumber: String = "",
    val addressTo: String = "",
    val orderedAt: String = "",
    val status: String = "",
)