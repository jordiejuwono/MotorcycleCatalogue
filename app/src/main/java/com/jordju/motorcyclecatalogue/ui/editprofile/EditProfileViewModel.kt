package com.jordju.motorcyclecatalogue.ui.editprofile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.data.model.User
import com.jordju.core.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val currentUserUseCase: GetCurrentUserUseCase,
    private val saveUserDataUseCase: SaveUserDataUseCase,
    private val getUserFullDataUseCase: GetUserFullDataUseCase,
    private val saveProfilePictureUseCase: SaveProfilePictureUseCase,
    private val fetchUserPhotoUseCase: FetchUserPhotoUseCase
) : ViewModel() {

    val saveUserDataState = MutableLiveData<Resource<Boolean>>()
    val currentUserState = MutableLiveData<Resource<FirebaseUser?>>()
    val userFullDataState = MutableLiveData<Resource<User?>>()
    val saveProfilePictureState = MutableLiveData<Resource<String>>()
    val photoState = MutableLiveData<Resource<Uri>>()

    fun getUserFullData() {
        viewModelScope.launch {
            getUserFullDataUseCase.execute().collect {
                userFullDataState.postValue(it)
            }
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            currentUserState.postValue(Resource.Loading())
            val currentUser = currentUserUseCase.execute()
            viewModelScope.launch(Dispatchers.Main) {
                try {
                    currentUserState.postValue(Resource.Success(currentUser))
                } catch (e: Exception) {
                    currentUserState.postValue(Resource.Error(e.message.orEmpty()))
                }
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

    fun fetchUserPhoto(userUid: String) {
        viewModelScope.launch {
            fetchUserPhotoUseCase.execute(userUid).collect {
                photoState.postValue(it)
            }
        }
    }
}