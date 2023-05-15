package com.jordju.core.domain.usecase

import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.domain.repository.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val repository: FirebaseRepository) {

    suspend fun execute(email: String, password: String): Flow<Resource<FirebaseUser?>> =
        repository.signInUser(email, password)

}