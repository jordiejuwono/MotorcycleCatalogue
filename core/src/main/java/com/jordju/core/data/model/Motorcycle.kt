package com.jordju.core.data.model

data class Motorcycle(
    var motorcycleId: String,
    var motorcycleName: String,
    var motorcycleImage: String,
    var imageList: List<String>,
    var category: String,
    var description: String,
    var displacement: Int,
    var oilTankCapacity: Double,
    var maximumPower: Double,
    var price: Double
)