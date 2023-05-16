package com.jordju.core.domain.usecase

import com.jordju.core.data.Resource
import com.jordju.core.data.local.entity.MotorcycleEntity
import com.jordju.core.domain.repository.MotorcycleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMotorcyclesUseCase @Inject constructor(private val repository: MotorcycleRepository) {

    fun execute(): Flow<Resource<List<MotorcycleEntity>>> =
        repository.getAllMotorcycles()

}