package com.jordju.motorcyclecatalogue.di

import com.jordju.core.domain.usecase.*
import com.jordju.motorcyclecatalogue.ui.authentication.login.LoginViewModel
import com.jordju.motorcyclecatalogue.ui.authentication.register.RegisterViewModel
import com.jordju.motorcyclecatalogue.ui.home.motorcyclelist.MotorcycleListViewModel
import com.jordju.motorcyclecatalogue.ui.home.profile.ProfileViewModel
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
    ): MotorcycleListViewModel {
        return MotorcycleListViewModel(motorcyclesUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideProfileViewModel(
        profileUserUseCase: GetCurrentUserUseCase,
    ): ProfileViewModel {
        return ProfileViewModel(profileUserUseCase)
    }
}