package com.jordju.core.domain.usecase

import com.jordju.core.domain.repository.MotorcycleRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(private val repository: MotorcycleRepository) {

    fun execute() = repository.logoutUser()

}