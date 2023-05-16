package com.jordju.core.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.data.local.LocalDataSource
import com.jordju.core.data.local.entity.MotorcycleEntity
import com.jordju.core.data.model.User
import com.jordju.core.data.remote.FirebaseDataSource
import com.jordju.core.domain.repository.MotorcycleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MotorcycleRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
    private val localDataSource: LocalDataSource
) :
    MotorcycleRepository {
    override suspend fun signInUser(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> = firebaseDataSource.signInUser(email, password)

    override suspend fun registerUser(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> = firebaseDataSource.registerUser(email, password)

    override suspend fun saveUserData(userReference: String, user: User): Flow<Resource<Boolean>> =
        firebaseDataSource.saveUserData(userReference, user)

    override suspend fun saveUserPhoto(userUid: String, imageUri: Uri): Flow<Resource<String>> =
        firebaseDataSource.saveUserPhoto(userUid, imageUri)

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseDataSource.getCurrentUser()
    }

    override suspend fun getUserFullData(): Flow<Resource<User?>> {
        return firebaseDataSource.getUserFullData()
    }

    override fun logoutUser() {
        return firebaseDataSource.logoutUser()
    }

    override fun getAllMotorcycles(): Flow<Resource<List<MotorcycleEntity>>> = flow {
        emit(Resource.Loading())

        localDataSource.getAllMotorcycles()
            .catch {
                emit(Resource.Error(it.message))
            }
            .collect {
                emit(Resource.Success(it))
            }
    }

    override suspend fun insertMotorcycleToWishlist(motorcycle: MotorcycleEntity) {
        return localDataSource.insertMotorcycleToWishlist(motorcycle)
    }

    override suspend fun deleteMotorcycleFromWishlist(motorcycle: MotorcycleEntity) {
        return localDataSource.deleteMotorcycleFromWishlist(motorcycle)
    }

    override fun isMotorcycleAlreadyExists(id: Int): Flow<Boolean> {
        return localDataSource.isMotorcycleAlreadyExists(id)
    }
}