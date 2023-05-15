package com.jordju.core.di

import com.jordju.core.domain.repository.FirebaseRepository
import com.jordju.core.domain.usecase.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: FirebaseRepository): RegisterUserUseCase {
        return RegisterUserUseCase(repository)
    }

}