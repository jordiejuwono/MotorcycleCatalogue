package com.jordju.core.domain.usecase

import com.jordju.core.data.Resource
import com.jordju.core.data.model.User
import com.jordju.core.domain.repository.MotorcycleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserFullDataUseCase @Inject constructor(private val repository: MotorcycleRepository) {

    suspend fun execute(): Flow<Resource<User?>> = repository.getUserFullData()

}