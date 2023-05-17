package com.jordju.motorcyclecatalogue.ui.checkout

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.data.model.MotorcycleOrderDetails
import com.jordju.core.data.model.User
import com.jordju.core.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val getUserFullDataUseCase: GetUserFullDataUseCase,
    private val currentUserUseCase: GetCurrentUserUseCase,
    private val sendMotorcycleOrderUseCase: SendMotorcycleOrderUseCase,
    private val saveUserDataUseCase: SaveUserDataUseCase,
) : ViewModel() {

    val userFullDataState = MutableLiveData<Resource<User?>>()
    val currentUserState = MutableLiveData<Resource<FirebaseUser?>>()
    val sendOrderState = MutableLiveData<Resource<Boolean>>()
    val saveUserDataState = MutableLiveData<Resource<Boolean>>()

    fun getUserFullData() {
        viewModelScope.launch {
            getUserFullDataUseCase.execute().collect {
                userFullDataState.postValue(it)
            }
        }
    }

    fun sendOrderMotorcycle(userReference: String, motorcycle: MotorcycleOrderDetails) {
        viewModelScope.launch {
            sendMotorcycleOrderUseCase.execute(userReference, motorcycle).collect {
                sendOrderState.postValue(it)
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