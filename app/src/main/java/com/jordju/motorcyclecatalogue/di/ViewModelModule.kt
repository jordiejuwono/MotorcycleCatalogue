package com.jordju.motorcyclecatalogue.di

import com.jordju.core.domain.usecase.*
import com.jordju.motorcyclecatalogue.ui.authentication.login.LoginViewModel
import com.jordju.motorcyclecatalogue.ui.authentication.register.RegisterViewModel
import com.jordju.motorcyclecatalogue.ui.checkout.CheckoutViewModel
import com.jordju.motorcyclecatalogue.ui.editprofile.EditProfileViewModel
import com.jordju.motorcyclecatalogue.ui.favorite.FavoriteActivity
import com.jordju.motorcyclecatalogue.ui.favorite.FavoriteViewModel
import com.jordju.motorcyclecatalogue.ui.home.motorcyclelist.MotorcycleListViewModel
import com.jordju.motorcyclecatalogue.ui.home.profile.ProfileViewModel
import com.jordju.motorcyclecatalogue.ui.home.transaction.TransactionViewModel
import com.jordju.motorcyclecatalogue.ui.orderdetail.OrderDetailViewModel
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
    fun provideLoginViewModel(useCase: LoginUserUseCase): LoginViewModel {
        return LoginViewModel(useCase)
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
        fetchFirebaseMessagingTokenUseCase: FetchFirebaseMessagingTokenUseCase,
        setMotorcycleFavoriteStatusUseCase: SetMotorcycleFavoriteStatusUseCase
    ): MotorcycleListViewModel {
        return MotorcycleListViewModel(motorcyclesUseCase, fetchFirebaseMessagingTokenUseCase, setMotorcycleFavoriteStatusUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideProfileViewModel(
        profileUserUseCase: GetCurrentUserUseCase,
        getUserFullDataUseCase: GetUserFullDataUseCase,
        logoutUserUseCase: LogoutUserUseCase,
        fetchUserPhotoUseCase: FetchUserPhotoUseCase
    ): ProfileViewModel {
        return ProfileViewModel(profileUserUseCase, getUserFullDataUseCase, logoutUserUseCase, fetchUserPhotoUseCase)
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
        saveProfilePictureUseCase: SaveProfilePictureUseCase,
        fetchUserPhotoUseCase: FetchUserPhotoUseCase
    ): EditProfileViewModel {
        return EditProfileViewModel(currentUserUseCase, saveUserDataUseCase, getUserFullDataUseCase, saveProfilePictureUseCase, fetchUserPhotoUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideCheckoutViewModel(
        getUserFullDataUseCase: GetUserFullDataUseCase,
        currentUserUseCase: GetCurrentUserUseCase,
        sendMotorcycleOrderUseCase: SendMotorcycleOrderUseCase,
        saveUserDataUseCase: SaveUserDataUseCase
    ): CheckoutViewModel {
        return CheckoutViewModel(getUserFullDataUseCase, currentUserUseCase, sendMotorcycleOrderUseCase, saveUserDataUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideTransactionViewModel(
        getCurrentUserUseCase: GetCurrentUserUseCase,
        getMotorcyclesOrderUseCase: GetMotorcyclesOrderUseCase
    ): TransactionViewModel {
        return TransactionViewModel(getCurrentUserUseCase, getMotorcyclesOrderUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideOrderDetailViewModel(
        cancelMotorcycleOrderUseCase: CancelMotorcycleOrderUseCase,
        getCurrentUserUseCase: GetCurrentUserUseCase
    ): OrderDetailViewModel {
        return OrderDetailViewModel(cancelMotorcycleOrderUseCase, getCurrentUserUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideFavoriteViewModel(
        getAllFavoriteMotorcyclesUseCase: GetAllFavoriteMotorcyclesUseCase,
        setMotorcycleFavoriteStatusUseCase: SetMotorcycleFavoriteStatusUseCase
    ): FavoriteViewModel {
        return FavoriteViewModel(getAllFavoriteMotorcyclesUseCase, setMotorcycleFavoriteStatusUseCase)
    }
}