package com.jordju.motorcyclecatalogue.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordju.core.data.Resource
import com.jordju.core.domain.entities.Motorcycle
import com.jordju.core.domain.usecase.GetAllFavoriteMotorcyclesUseCase
import com.jordju.core.domain.usecase.SetMotorcycleFavoriteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFavoriteMotorcyclesUseCase: GetAllFavoriteMotorcyclesUseCase,
    private val setMotorcycleFavoriteStatusUseCase: SetMotorcycleFavoriteStatusUseCase
) : ViewModel() {

    val favoriteState = MutableLiveData<Resource<List<Motorcycle>>>()
    val setStateFavorite = MutableLiveData<Resource<Boolean>>()

    fun getAllFavoriteMotorcycles() {
        viewModelScope.launch {
            getAllFavoriteMotorcyclesUseCase.execute().collect {
                favoriteState.postValue(it)
            }
        }
    }

    fun setFavoriteStatus(motorcycleId: Int, setToFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            setStateFavorite.postValue(Resource.Loading())
            setMotorcycleFavoriteStatusUseCase.execute(motorcycleId, setToFavorite)
            viewModelScope.launch(Dispatchers.Main) {
                try {
                    setStateFavorite.postValue(Resource.Success(true))
                }catch (e: Exception) {
                    setStateFavorite.postValue(Resource.Error(e.message))
                }
            }
        }
    }
}