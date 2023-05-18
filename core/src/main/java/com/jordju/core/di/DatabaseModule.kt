package com.jordju.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jordju.core.data.local.room.dao.MotorcycleDao
import com.jordju.core.data.local.room.MotorcycleDatabase
import com.jordju.core.utils.InitialDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob())

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context, applicationScope: CoroutineScope): MotorcycleDatabase {
        var INSTANCE: MotorcycleDatabase? = null

        if (INSTANCE == null) {
            synchronized(MotorcycleDatabase::class.java) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MotorcycleDatabase::class.java, "Motorcycle.db"
                ).fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            INSTANCE?.let { db ->
                                applicationScope.launch {
                                    val dao = db.motorcycleDao()
                                    dao.insertMotorcycles(InitialDataSource.getMotorcycleList())
                                }
                            }
                        }
                    })
                    .build()
            }
        }

        return INSTANCE as MotorcycleDatabase
    }

    @Provides
    @Singleton
    fun provideMotorcycleDao(database: MotorcycleDatabase): MotorcycleDao = database.motorcycleDao()

}