package com.jordju.core.domain.usecase

import com.jordju.core.domain.repository.MotorcycleRepository

class GetCurrentUserUseCase(private val repository: MotorcycleRepository) {

    fun execute() = repository.getCurrentUser()

}