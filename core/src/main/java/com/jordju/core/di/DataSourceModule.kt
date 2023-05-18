package com.jordju.core.di

import com.jordju.core.data.local.LocalDataSource
import com.jordju.core.data.local.LocalDataSourceImpl
import com.jordju.core.data.remote.FirebaseDataSource
import com.jordju.core.data.remote.FirebaseDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindFirebaseDataSource(firebaseDataSourceImpl: FirebaseDataSourceImpl): FirebaseDataSource

    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

}