package com.jordju.core.domain.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.data.local.entity.MotorcycleEntity
import com.jordju.core.data.model.MotorcycleOrderDetails
import com.jordju.core.data.model.User
import kotlinx.coroutines.flow.Flow

interface MotorcycleRepository {
    // firebase
    suspend fun signInUser(email: String, password: String): Flow<Resource<FirebaseUser?>>
    suspend fun registerUser(email: String, password: String): Flow<Resource<FirebaseUser?>>
    suspend fun saveUserData(userReference: String, user: User): Flow<Resource<Boolean>>
    suspend fun saveUserPhoto(userUid: String, imageUri: Uri): Flow<Resource<String>>
    suspend fun sendMotorcycleOrder(userReference: String, motorcycle: MotorcycleOrderDetails): Flow<Resource<String>>
    suspend fun getMotorcyclesOrder(userReference: String): Flow<Resource<List<MotorcycleOrderDetails>>>
    fun getCurrentUser(): FirebaseUser?
    suspend fun fetchFirebaseMessagingToken(): Flow<Resource<String>>
    suspend fun subscribeToTopic(uid: String): Flow<Resource<Boolean>>
    suspend fun sendDataToTopic(uid: String, title: String, message: String): Flow<Resource<Boolean>>
    suspend fun getUserFullData(): Flow<Resource<User?>>
    fun logoutUser()

    // local
    fun getAllMotorcycles(): Flow<Resource<List<MotorcycleEntity>>>
    suspend fun insertMotorcycleToWishlist(motorcycle: MotorcycleEntity)
    suspend fun deleteMotorcycleFromWishlist(motorcycle: MotorcycleEntity)
    fun isMotorcycleAlreadyExists(id: Int): Flow<Boolean>
}