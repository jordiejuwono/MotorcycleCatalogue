package com.jordju.core.domain.usecase

import com.jordju.core.domain.repository.MotorcycleRepository
import javax.inject.Inject

class SendDataToTopicUseCase @Inject constructor(private val repository: MotorcycleRepository) {

    suspend fun execute(uid: String, title: String, message: String) = repository.sendDataToTopic(uid, title, message)

}