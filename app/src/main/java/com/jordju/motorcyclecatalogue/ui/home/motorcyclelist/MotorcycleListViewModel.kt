package com.jordju.motorcyclecatalogue.ui.home.motorcyclelist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordju.core.data.Resource
import com.jordju.core.data.local.entity.MotorcycleEntity
import com.jordju.core.domain.usecase.FetchFirebaseMessagingTokenUseCase
import com.jordju.core.domain.usecase.GetAllMotorcyclesUseCase
import com.jordju.core.domain.usecase.SubscribeToTopicUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MotorcycleListViewModel @Inject constructor(
    private val useCase: GetAllMotorcyclesUseCase,
    private val fcmTokenUseCase: FetchFirebaseMessagingTokenUseCase,
) : ViewModel() {

    val motorcyclesState = MutableLiveData<Resource<List<MotorcycleEntity>>>()

    fun getAllMotorcycles() {
        viewModelScope.launch {
            useCase.execute().collect {
                motorcyclesState.postValue(it)
            }
        }
    }

    fun getFcmToken() {
        viewModelScope.launch {
            fcmTokenUseCase.execute().collect {
                Log.d("TEST TOKEN", "getFcmToken: ${it.data}")
            }
        }
    }




}