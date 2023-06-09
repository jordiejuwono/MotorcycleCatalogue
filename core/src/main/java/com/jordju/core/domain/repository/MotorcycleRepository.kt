package com.jordju.core.domain.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.data.model.MotorcycleOrderDetails
import com.jordju.core.data.model.User
import com.jordju.core.domain.entities.Motorcycle
import kotlinx.coroutines.flow.Flow

interface MotorcycleRepository {
    // firebase
    suspend fun signInUser(email: String, password: String): Flow<Resource<FirebaseUser?>>
    suspend fun registerUser(email: String, password: String): Flow<Resource<FirebaseUser?>>
    suspend fun saveUserData(userReference: String, user: User): Flow<Resource<Boolean>>
    suspend fun saveUserPhoto(userUid: String, imageUri: Uri): Flow<Resource<String>>
    suspend fun fetchUserPhoto(userUid: String): Flow<Resource<Uri>>
    suspend fun sendMotorcycleOrder(userReference: String, motorcycle: MotorcycleOrderDetails): Flow<Resource<Boolean>>
    suspend fun getMotorcyclesOrder(userReference: String): Flow<Resource<List<MotorcycleOrderDetails>>>
    suspend fun cancelMotorcycleOrder(userReference: String, orderId: String): Flow<Resource<Boolean>>
    fun getCurrentUser(): FirebaseUser?
    suspend fun fetchFirebaseMessagingToken(): Flow<Resource<String>>
    suspend fun getUserFullData(): Flow<Resource<User?>>
    fun logoutUser()

    // local
    fun getAllMotorcycles(): Flow<Resource<List<Motorcycle>>>
    suspend fun getAllFavoriteMotorcycles(): Flow<Resource<List<Motorcycle>>>
    suspend fun deleteMotorcycleFromWishlist(motorcycle: Motorcycle)
    fun isMotorcycleAlreadyExists(id: Int): Flow<Boolean>
    suspend fun setMotorcycleFavoriteStatus(motorcycleId: Int, setToFavorite: Boolean)
}