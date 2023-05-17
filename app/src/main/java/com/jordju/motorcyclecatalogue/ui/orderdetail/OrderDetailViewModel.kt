package com.jordju.motorcyclecatalogue.ui.orderdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.data.model.User
import com.jordju.core.domain.usecase.CancelMotorcycleOrderUseCase
import com.jordju.core.domain.usecase.GetCurrentUserUseCase
import com.jordju.core.domain.usecase.GetUserFullDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    private val cancelMotorcycleOrderUseCase: CancelMotorcycleOrderUseCase,
    private val currentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    val cancelOrderState = MutableLiveData<Resource<Boolean>>()
    val currentUserState = MutableLiveData<Resource<FirebaseUser?>>()

    fun cancelOrder(userReference: String, orderId: String) {
        viewModelScope.launch {
            cancelMotorcycleOrderUseCase.execute(userReference, orderId).collect {
                cancelOrderState.postValue(it)
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
}