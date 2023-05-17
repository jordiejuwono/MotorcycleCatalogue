package com.jordju.core.domain.usecase

import com.jordju.core.domain.repository.MotorcycleRepository
import javax.inject.Inject

class SubscribeToTopicUseCase @Inject constructor(
    private val repository: MotorcycleRepository
) {

    suspend fun execute(uid: String) = repository.subscribeToTopic(uid)

}