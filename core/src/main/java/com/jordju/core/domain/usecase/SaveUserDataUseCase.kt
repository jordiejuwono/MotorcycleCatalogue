package com.jordju.core.domain.usecase

import com.jordju.core.data.Resource
import com.jordju.core.data.model.User
import com.jordju.core.domain.repository.MotorcycleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveUserDataUseCase @Inject constructor(private val repository: MotorcycleRepository) {

    suspend fun execute(user: User): Flow<Resource<Boolean>> =
        repository.saveUserData(user)

}