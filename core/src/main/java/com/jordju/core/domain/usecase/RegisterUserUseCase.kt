package com.jordju.core.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.domain.repository.MotorcycleRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: MotorcycleRepository) {
    suspend fun execute(email: String, password: String): Flow<Resource<FirebaseUser?>> =
        repository.registerUser(email, password)
}