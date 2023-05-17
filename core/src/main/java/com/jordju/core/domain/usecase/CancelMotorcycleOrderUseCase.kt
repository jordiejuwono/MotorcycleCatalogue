package com.jordju.core.domain.usecase

import com.jordju.core.domain.repository.MotorcycleRepository
import javax.inject.Inject

class CancelMotorcycleOrderUseCase @Inject constructor(
    private val repository: MotorcycleRepository
) {

    suspend fun execute(userReference: String, orderId: String) =
        repository.cancelMotorcycleOrder(userReference, orderId)

}