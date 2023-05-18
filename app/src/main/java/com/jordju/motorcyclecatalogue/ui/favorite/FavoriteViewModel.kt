package com.jordju.motorcyclecatalogue.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordju.core.data.Resource
import com.jordju.core.data.local.room.entity.MotorcycleEntity
import com.jordju.core.domain.usecase.GetAllFavoriteMotorcyclesUseCase
import com.jordju.core.domain.usecase.SetMotorcycleFavoriteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFavoriteMotorcyclesUseCase: GetAllFavoriteMotorcyclesUseCase,
    private val setMotorcycleFavoriteStatusUseCase: SetMotorcycleFavoriteStatusUseCase
) : ViewModel() {

    val favoriteState = MutableLiveData<Resource<List<MotorcycleEntity>>>()

    fun getAllFavoriteMotorcycles() {
        viewModelScope.launch {
            getAllFavoriteMotorcyclesUseCase.execute().collect {
                favoriteState.postValue(it)
            }
        }
    }

    fun setFavoriteStatus(motorcycleId: Int, setToFavorite: Boolean) {
        viewModelScope.launch {
            setMotorcycleFavoriteStatusUseCase.execute(motorcycleId, setToFavorite)
        }
    }
}