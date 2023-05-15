package com.jordju.motorcyclecatalogue.di

import com.jordju.core.domain.usecase.LoginUserUseCase
import com.jordju.core.domain.usecase.RegisterUserUseCase
import com.jordju.core.domain.usecase.SaveProfilePictureUseCase
import com.jordju.core.domain.usecase.SaveUserDataUseCase
import com.jordju.motorcyclecatalogue.ui.authentication.login.LoginViewModel
import com.jordju.motorcyclecatalogue.ui.authentication.register.RegisterViewModel
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

}