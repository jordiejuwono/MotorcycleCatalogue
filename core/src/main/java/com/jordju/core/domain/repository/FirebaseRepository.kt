package com.jordju.core.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    suspend fun signInUser(email: String, password: String): Flow<Resource<FirebaseUser?>>
    suspend fun registerUser(email: String, password: String): Flow<Resource<FirebaseUser?>>
    fun getCurrentUser(): FirebaseUser?
    fun logoutUser()
}