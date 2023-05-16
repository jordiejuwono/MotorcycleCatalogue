package com.jordju.motorcyclecatalogue.ui.home.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.domain.usecase.GetCurrentUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val currentUserUseCase: GetCurrentUserUseCase) :
    ViewModel() {

    val currentUserState = MutableLiveData<Resource<FirebaseUser?>>()

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

}