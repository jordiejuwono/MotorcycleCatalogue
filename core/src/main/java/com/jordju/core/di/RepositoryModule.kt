package com.jordju.core.di

import com.jordju.core.data.remote.FirebaseDataSource
import com.jordju.core.data.repository.FirebaseRepositoryImpl
import com.jordju.core.domain.repository.FirebaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFirebaseRepository(dataSource: FirebaseDataSource): FirebaseRepository {
        return FirebaseRepositoryImpl(dataSource)
    }

}