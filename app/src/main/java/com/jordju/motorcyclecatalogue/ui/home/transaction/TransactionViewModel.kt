package com.jordju.motorcyclecatalogue.ui.home.transaction

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.data.model.MotorcycleOrderDetails
import com.jordju.core.domain.usecase.GetCurrentUserUseCase
import com.jordju.core.domain.usecase.GetMotorcyclesOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val currentUserUseCase: GetCurrentUserUseCase,
    private val getMotorcyclesOrderUseCase: GetMotorcyclesOrderUseCase
) : ViewModel() {

    val transactionListState = MutableLiveData<Resource<List<MotorcycleOrderDetails>>>()
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

    fun getMotorcyclesOrder(userReference: String) {
        viewModelScope.launch {
            getMotorcyclesOrderUseCase.execute(userReference).collect {
                transactionListState.postValue(it)
                Log.d("TAG TEST", "getMotorcyclesOrder: $it")
            }
        }
    }
}