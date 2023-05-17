package com.jordju.motorcyclecatalogue.ui.editprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.data.model.User
import com.jordju.core.domain.usecase.GetCurrentUserUseCase
import com.jordju.core.domain.usecase.GetUserFullDataUseCase
import com.jordju.core.domain.usecase.SaveUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val currentUserUseCase: GetCurrentUserUseCase,
    private val saveUserDataUseCase: SaveUserDataUseCase,
    private val getUserFullDataUseCase: GetUserFullDataUseCase,
) : ViewModel() {


    val saveUserDataState = MutableLiveData<Resource<Boolean>>()
    val currentUserState = MutableLiveData<Resource<FirebaseUser?>>()
    val userFullDataState = MutableLiveData<Resource<User?>>()

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

}