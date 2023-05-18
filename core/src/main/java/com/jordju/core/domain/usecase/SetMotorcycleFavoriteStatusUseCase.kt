package com.jordju.core.domain.usecase

import com.jordju.core.domain.repository.MotorcycleRepository
import javax.inject.Inject

class SetMotorcycleFavoriteStatusUseCase @Inject constructor(
    private val repository: MotorcycleRepository
) {

    suspend fun execute(motorcycleId: Int, setToFavorite: Boolean) =
        repository.setMotorcycleFavoriteStatus(motorcycleId, setToFavorite)

}