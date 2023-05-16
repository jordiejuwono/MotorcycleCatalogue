package com.jordju.core.data.model


data class Motorcycle(
    var motorcycleName: String,
    var motorcycleImage: String,
    var description: String,
    var category: String,
    var displacement: Int,
    var oilTankCapacity: Double,
    var price: Double
)