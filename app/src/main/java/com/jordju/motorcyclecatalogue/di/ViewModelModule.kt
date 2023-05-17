package com.jordju.motorcyclecatalogue.di

import com.jordju.core.domain.usecase.*
import com.jordju.motorcyclecatalogue.ui.authentication.login.LoginViewModel
import com.jordju.motorcyclecatalogue.ui.authentication.register.RegisterViewModel
import com.jordju.motorcyclecatalogue.ui.checkout.CheckoutViewModel
import com.jordju.motorcyclecatalogue.ui.editprofile.EditProfileViewModel
import com.jordju.motorcyclecatalogue.ui.home.motorcyclelist.MotorcycleListViewModel
import com.jordju.motorcyclecatalogue.ui.home.profile.ProfileViewModel
import com.jordju.motorcyclecatalogue.ui.home.transaction.TransactionViewModel
import com.jordju.motorcyclecatalogue.ui.splashscreen.SplashScreenViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideLoginViewModel(useCase: LoginUserUseCase,
    subscribeToTopicUseCase: SubscribeToTopicUseCase): LoginViewModel {
        return LoginViewModel(useCase, subscribeToTopicUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideRegisterUseCase(
        registerUseCase: RegisterUserUseCase,
        userDataUseCase: SaveUserDataUseCase,
        profilePictureUseCase: SaveProfilePictureUseCase
    ): RegisterViewModel {
        return RegisterViewModel(
            registerUseCase,
            userDataUseCase,
            profilePictureUseCase
        )
    }

    @Provides
    @ViewModelScoped
    fun provideMotorcycleListViewModel(
        motorcyclesUseCase: GetAllMotorcyclesUseCase,
        fetchFirebaseMessagingTokenUseCase: FetchFirebaseMessagingTokenUseCase
    ): MotorcycleListViewModel {
        return MotorcycleListViewModel(motorcyclesUseCase, fetchFirebaseMessagingTokenUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideProfileViewModel(
        profileUserUseCase: GetCurrentUserUseCase,
        getUserFullDataUseCase: GetUserFullDataUseCase,
        logoutUserUseCase: LogoutUserUseCase
    ): ProfileViewModel {
        return ProfileViewModel(profileUserUseCase, getUserFullDataUseCase, logoutUserUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideSplashScreenViewModel(
        profileUserUseCase: GetCurrentUserUseCase,
    ): SplashScreenViewModel {
        return SplashScreenViewModel(profileUserUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideEditProfileViewModel(
        currentUserUseCase: GetCurrentUserUseCase,
        saveUserDataUseCase: SaveUserDataUseCase,
        getUserFullDataUseCase: GetUserFullDataUseCase,
    ): EditProfileViewModel {
        return EditProfileViewModel(currentUserUseCase, saveUserDataUseCase, getUserFullDataUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideCheckoutViewModel(
        getUserFullDataUseCase: GetUserFullDataUseCase,
        sendDataToTopicUseCase: SendDataToTopicUseCase,
        currentUserUseCase: GetCurrentUserUseCase,
        sendMotorcycleOrderUseCase: SendMotorcycleOrderUseCase
    ): CheckoutViewModel {
        return CheckoutViewModel(getUserFullDataUseCase, sendDataToTopicUseCase, currentUserUseCase, sendMotorcycleOrderUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideTransactionViewModel(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        getMotorcyclesOrderUseCase: GetMotorcyclesOrderUseCase
    ): TransactionViewModel {
        return TransactionViewModel(getCurrentUserUseCase, getMotorcyclesOrderUseCase)
    }
}