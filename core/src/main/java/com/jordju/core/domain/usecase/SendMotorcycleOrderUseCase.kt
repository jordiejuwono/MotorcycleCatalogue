package com.jordju.core.domain.usecase

import com.jordju.core.data.model.MotorcycleOrderDetails
import com.jordju.core.domain.repository.MotorcycleRepository
import javax.inject.Inject

class SendMotorcycleOrderUseCase @Inject constructor(
    private val repository: MotorcycleRepository
) {

    suspend fun execute(userReference: String, motorcycle: MotorcycleOrderDetails) =
        repository.sendMotorcycleOrder(userReference, motorcycle)

}