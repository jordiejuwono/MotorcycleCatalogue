package com.jordju.core.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Motorcycle(
    var motorcycleId: Int,
    var motorcycleName: String,
    var motorcycleImage: String,
    var imageList: List<String>,
    var category: String,
    var description: String,
    var displacement: Int,
    var oilTankCapacity: Double,
    var maximumPower: Double,
    var price: Double,
    var isFavorite: Boolean
) : Parcelable