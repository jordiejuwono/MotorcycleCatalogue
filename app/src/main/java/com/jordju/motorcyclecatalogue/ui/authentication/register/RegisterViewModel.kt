package com.jordju.motorcyclecatalogue.ui.authentication.register

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.data.model.User
import com.jordju.core.domain.usecase.RegisterUserUseCase
import com.jordju.core.domain.usecase.SaveProfilePictureUseCase
import com.jordju.core.domain.usecase.SaveUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUserUseCase,
    private val saveUserDataUseCase: SaveUserDataUseCase,
    private val saveProfilePictureUseCase: SaveProfilePictureUseCase,
) :
    ViewModel() {

    val registerState = MutableLiveData<Resource<FirebaseUser?>>()
    val saveProfilePictureState = MutableLiveData<Resource<String>>()
    val saveUserDataState = MutableLiveData<Resource<Boolean>>()

    fun registerUser(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            registerUseCase.execute(email, password).collect {
                registerState.postValue(it)
            }
        }
    }

    fun saveUserData(dataReference: String, user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            saveUserDataUseCase.execute(dataReference, user).collect {
                saveUserDataState.postValue(it)
            }
        }
    }

    fun saveProfilePicture(userUid: String, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            saveProfilePictureUseCase.execute(userUid, uri).collect {
                saveProfilePictureState.postValue(it)
            }
        }
    }

}