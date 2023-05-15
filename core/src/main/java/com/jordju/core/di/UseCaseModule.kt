package com.jordju.core.di

import com.jordju.core.domain.repository.FirebaseRepository
import com.jordju.core.domain.usecase.LoginUserUseCase
import com.jordju.core.domain.usecase.RegisterUserUseCase
import com.jordju.core.domain.usecase.SaveProfilePictureUseCase
import com.jordju.core.domain.usecase.SaveUserDataUseCase
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

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: FirebaseRepository): LoginUserUseCase {
        return LoginUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveUserDataUseCase(repository: FirebaseRepository): SaveUserDataUseCase {
        return SaveUserDataUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveProfilePictureUseCase(repository: FirebaseRepository): SaveProfilePictureUseCase {
        return SaveProfilePictureUseCase(repository)
    }

}