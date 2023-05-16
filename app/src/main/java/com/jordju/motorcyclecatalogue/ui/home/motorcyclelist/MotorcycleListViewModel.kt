package com.jordju.motorcyclecatalogue.ui.home.motorcyclelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordju.core.data.Resource
import com.jordju.core.data.local.entity.MotorcycleEntity
import com.jordju.core.domain.usecase.GetAllMotorcyclesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MotorcycleListViewModel @Inject constructor(private val useCase: GetAllMotorcyclesUseCase): ViewModel() {

    val motorcyclesState = MutableLiveData<Resource<List<MotorcycleEntity>>>()

    fun getAllMotorcycles() {
        viewModelScope.launch {
            useCase.execute().collect {
                motorcyclesState.postValue(it)
            }
        }
    }


}