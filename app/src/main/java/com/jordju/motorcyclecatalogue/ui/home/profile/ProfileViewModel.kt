package com.jordju.motorcyclecatalogue.ui.home.profile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.data.model.User
import com.jordju.core.domain.usecase.FetchUserPhotoUseCase
import com.jordju.core.domain.usecase.GetCurrentUserUseCase
import com.jordju.core.domain.usecase.GetUserFullDataUseCase
import com.jordju.core.domain.usecase.LogoutUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val currentUserUseCase: GetCurrentUserUseCase,
    private val getUserFullDataUseCase: GetUserFullDataUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
    private val fetchUserPhotoUseCase: FetchUserPhotoUseCase
) :
    ViewModel() {

    val currentUserState = MutableLiveData<Resource<FirebaseUser?>>()
    val userFullDataState = MutableLiveData<Resource<User?>>()
    val logoutState = MutableLiveData<Resource<Boolean>>()
    val photoState = MutableLiveData<Resource<Uri>>()

    fun getCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            currentUserState.postValue(Resource.Loading())
            val currentUser = currentUserUseCase.execute()
            viewModelScope.launch(Dispatchers.Main) {
                try {
                    currentUserState.postValue(Resource.Success(currentUser))
                    fetchUserPhotoUseCase.execute(currentUser?.uid ?: "").collect {
                        photoState.postValue(it)
                    }
                } catch (e: Exception) {
                    currentUserState.postValue(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }

    fun getUserFullData() {
        viewModelScope.launch {
            getUserFullDataUseCase.execute().collect {
                userFullDataState.postValue(it)
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

    fun logoutUser() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutState.postValue(Resource.Loading())
            viewModelScope.launch(Dispatchers.Main) {
                try {
                    logoutUserUseCase.execute()
                    logoutState.postValue(Resource.Success(true))
                } catch (e: Exception) {
                    logoutState.postValue(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }

}