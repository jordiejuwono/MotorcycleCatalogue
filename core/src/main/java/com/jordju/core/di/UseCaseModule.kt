package com.jordju.core.di

import com.jordju.core.domain.repository.MotorcycleRepository
import com.jordju.core.domain.usecase.*
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
    fun provideRegisterUseCase(repository: MotorcycleRepository): RegisterUserUseCase {
        return RegisterUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: MotorcycleRepository): LoginUserUseCase {
        return LoginUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveUserDataUseCase(repository: MotorcycleRepository): SaveUserDataUseCase {
        return SaveUserDataUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveProfilePictureUseCase(repository: MotorcycleRepository): SaveProfilePictureUseCase {
        return SaveProfilePictureUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUserUseCase(repository: MotorcycleRepository): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(repository)
    }

}