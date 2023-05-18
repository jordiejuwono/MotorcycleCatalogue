package com.jordju.core.data.local.room.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "motorcycle")
@Parcelize
data class MotorcycleEntity(

    @PrimaryKey
    @ColumnInfo(name = "motorcycle_id")
    var motorcycleId: Int,

    @ColumnInfo(name = "motorcycle_name")
    var motorcycleName: String,

    @ColumnInfo(name = "motorcycle_image")
    var motorcycleImage: String,

    @ColumnInfo(name = "image_list")
    var imageList: List<String>,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "displacement")
    var displacement: Int,

    @ColumnInfo(name = "oil_capacity")
    var oilTankCapacity: Double,

    @ColumnInfo(name = "maximum_power")
    var maximumPower: Double,

    @ColumnInfo(name = "price")
    var price: Double,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean

) : Parcelable