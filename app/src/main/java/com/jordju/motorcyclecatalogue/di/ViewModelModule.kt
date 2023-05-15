package com.jordju.motorcyclecatalogue.di

import com.jordju.core.domain.usecase.RegisterUserUseCase
import com.jordju.motorcyclecatalogue.ui.authentication.LoginViewModel
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
    fun provideLoginViewModel(useCase: RegisterUserUseCase): LoginViewModel {
        return LoginViewModel(useCase)
    }

}