package com.jordju.core.utils

import com.jordju.core.data.local.room.entity.MotorcycleEntity
import com.jordju.core.domain.entities.Motorcycle

object DataMapper {

    fun mapListMotorcycleEntity(
        motorcycleList: List<MotorcycleEntity>
    ) : List<Motorcycle> {
        val resultList = mutableListOf<Motorcycle>()
        motorcycleList.map {
            val motorcycle = Motorcycle(
                motorcycleId = it.motorcycleId,
                motorcycleName = it.motorcycleName,
                motorcycleImage = it.motorcycleImage,
                imageList = it.imageList,
                category = it.category,
                description = it.description,
                displacement = it.displacement,
                oilTankCapacity = it.oilTankCapacity,
                maximumPower = it.maximumPower,
                price = it.price,
                isFavorite = it.isFavorite,
            )
            resultList.add(motorcycle)
        }

        return resultList
    }

    fun mapMotorcycleToEntity(
        motorcycle: Motorcycle
    ): MotorcycleEntity {
        return MotorcycleEntity(
            motorcycleId = motorcycle.motorcycleId,
            motorcycleName = motorcycle.motorcycleName,
            motorcycleImage = motorcycle.motorcycleImage,
            imageList = motorcycle.imageList,
            category = motorcycle.category,
            description = motorcycle.description,
            displacement = motorcycle.displacement,
            oilTankCapacity = motorcycle.oilTankCapacity,
            maximumPower = motorcycle.maximumPower,
            price = motorcycle.price,
            isFavorite = motorcycle.isFavorite,
        )
    }

}