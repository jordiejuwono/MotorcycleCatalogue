package com.jordju.core.di

import android.content.Context
import androidx.room.Room
import com.jordju.core.data.local.room.MotorcycleDao
import com.jordju.core.data.local.room.MotorcycleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): MotorcycleDatabase {
        return Room.databaseBuilder(
            context,
            MotorcycleDatabase::class.java, "Motorcycle.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMotorcycleDao(database: MotorcycleDatabase): MotorcycleDao = database.motorcycleDao()

}