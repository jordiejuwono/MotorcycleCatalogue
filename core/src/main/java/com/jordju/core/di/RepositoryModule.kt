package com.jordju.core.di

import com.jordju.core.data.repository.MotorcycleRepositoryImpl
import com.jordju.core.domain.repository.MotorcycleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepositoryModule(repositoryImpl: MotorcycleRepositoryImpl): MotorcycleRepository

}