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

    @Provides
    @Singleton
    fun provideGetUserFullDataUseCase(repository: MotorcycleRepository): GetUserFullDataUseCase {
        return GetUserFullDataUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLogoutUserUseCase(repository: MotorcycleRepository): LogoutUserUseCase {
        return LogoutUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFetchFirebaseMessagingUseCase(repository: MotorcycleRepository): FetchFirebaseMessagingTokenUseCase {
        return FetchFirebaseMessagingTokenUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSendDataToTopicUseCase(repository: MotorcycleRepository): SendDataToTopicUseCase {
        return SendDataToTopicUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSubscribeToTopicUseCase(repository: MotorcycleRepository): SubscribeToTopicUseCase {
        return SubscribeToTopicUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSendMotorcycleOrderUseCase(repository: MotorcycleRepository): SendMotorcycleOrderUseCase {
        return SendMotorcycleOrderUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCancelMotorcycleOrderUseCase(repository: MotorcycleRepository): CancelMotorcycleOrderUseCase {
        return CancelMotorcycleOrderUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFetchUserPhotoUseCase(repository: MotorcycleRepository): FetchUserPhotoUseCase {
        return FetchUserPhotoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetMotorcycleFavoriteStatusUseCase(repository: MotorcycleRepository): SetMotorcycleFavoriteStatusUseCase {
        return SetMotorcycleFavoriteStatusUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllFavoriteMotorcycles(repository: MotorcycleRepository): GetAllFavoriteMotorcyclesUseCase {
        return GetAllFavoriteMotorcyclesUseCase(repository)
    }

}