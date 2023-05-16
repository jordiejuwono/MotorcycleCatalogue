package com.jordju.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jordju.core.data.local.entity.MotorcycleEntity

@Database(entities = [MotorcycleEntity::class], version = 1, exportSchema = false)
@TypeConverters(com.jordju.core.data.local.room.converters.Converters::class)
abstract class MotorcycleDatabase: RoomDatabase() {

    abstract fun motorcycleDao(): MotorcycleDao

}