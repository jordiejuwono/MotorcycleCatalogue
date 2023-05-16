package com.jordju.motorcyclecatalogue.ui.splashscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.domain.usecase.GetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val useCase: GetCurrentUserUseCase
) : ViewModel() {


    fun isUserLoggedIn(): Boolean {
        return useCase.execute() != null
    }

}