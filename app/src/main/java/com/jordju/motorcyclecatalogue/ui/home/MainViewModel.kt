package com.jordju.motorcyclecatalogue.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.data.local.entity.MotorcycleEntity
import com.jordju.core.domain.usecase.GetAllMotorcyclesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: GetAllMotorcyclesUseCase): ViewModel() {

    val motorcyclesState = MutableLiveData<Resource<List<MotorcycleEntity>>>()

    fun getAllMotorcycles() {
        viewModelScope.launch {
            useCase.execute().collect {
                motorcyclesState.postValue(it)
            }
        }
    }


}