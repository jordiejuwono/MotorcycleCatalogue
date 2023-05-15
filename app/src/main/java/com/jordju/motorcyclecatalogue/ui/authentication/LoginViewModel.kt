package com.jordju.motorcyclecatalogue.ui.authentication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.jordju.core.data.Resource
import com.jordju.core.domain.usecase.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCase: RegisterUserUseCase): ViewModel() {

    val loginState = MutableLiveData<Resource<FirebaseUser?>>()

    fun registerUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.execute(email, password).collect {
                loginState.postValue(it)
            }
        }
    }

}