package com.jordju.core.domain.usecase

import com.jordju.core.domain.repository.MotorcycleRepository
import javax.inject.Inject

class FetchUserPhotoUseCase @Inject constructor(
    private val repository: MotorcycleRepository
) {

    suspend fun execute(userUid: String) = repository.fetchUserPhoto(userUid)

}