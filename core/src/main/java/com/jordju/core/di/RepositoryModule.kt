package com.jordju.core.di

import com.jordju.core.data.local.LocalDataSource
import com.jordju.core.data.remote.FirebaseDataSource
import com.jordju.core.data.repository.MotorcycleRepositoryImpl
import com.jordju.core.domain.repository.MotorcycleRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMotorcycleRepository(motorcycleRepositoryImpl: MotorcycleRepositoryImpl): MotorcycleRepository

}