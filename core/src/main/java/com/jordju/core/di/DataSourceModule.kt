package com.jordju.core.di

import com.google.firebase.auth.FirebaseAuth
import com.jordju.core.data.local.LocalDataSource
import com.jordju.core.data.local.LocalDataSourceImpl
import com.jordju.core.data.local.room.dao.MotorcycleDao
import com.jordju.core.data.remote.FirebaseDataSource
import com.jordju.core.data.remote.FirebaseDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideFirebaseDataSource(auth: FirebaseAuth): FirebaseDataSource {
        return FirebaseDataSourceImpl(auth)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(dao: MotorcycleDao): LocalDataSource {
        return LocalDataSourceImpl(dao)
    }

}