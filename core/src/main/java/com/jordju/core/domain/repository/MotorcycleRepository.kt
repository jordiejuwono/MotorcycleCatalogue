package com.jordju.core.domain.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.data.local.entity.MotorcycleEntity
import com.jordju.core.data.model.User
import kotlinx.coroutines.flow.Flow

interface MotorcycleRepository {
    // firebase
    suspend fun signInUser(email: String, password: String): Flow<Resource<FirebaseUser?>>
    suspend fun registerUser(email: String, password: String): Flow<Resource<FirebaseUser?>>
    suspend fun saveUserData(user: User): Flow<Resource<Boolean>>
    suspend fun saveUserPhoto(imageUri: Uri): Flow<Resource<String>>
    fun getCurrentUser(): FirebaseUser?
    fun logoutUser()

    // local
    fun getAllMotorcycles(): Flow<Resource<List<MotorcycleEntity>>>
    suspend fun insertMotorcycleToWishlist(motorcycle: MotorcycleEntity)
    suspend fun deleteMotorcycleFromWishlist(motorcycle: MotorcycleEntity)
    fun isMotorcycleAlreadyExists(id: Int): Flow<Boolean>
}