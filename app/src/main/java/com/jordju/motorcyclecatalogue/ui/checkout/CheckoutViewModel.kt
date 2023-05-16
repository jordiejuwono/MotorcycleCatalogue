package com.jordju.motorcyclecatalogue.ui.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordju.core.data.Resource
import com.jordju.core.data.model.User
import com.jordju.core.domain.usecase.GetUserFullDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val getUserFullDataUseCase: GetUserFullDataUseCase
) : ViewModel() {

    val userFullDataState = MutableLiveData<Resource<User?>>()

    fun getUserFullData() {
        viewModelScope.launch {
            getUserFullDataUseCase.execute().collect {
                userFullDataState.postValue(it)
            }
        }
    }

}