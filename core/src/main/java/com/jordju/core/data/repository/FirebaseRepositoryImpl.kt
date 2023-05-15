package com.jordju.core.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import com.jordju.core.data.model.User
import com.jordju.core.data.remote.FirebaseDataSource
import com.jordju.core.domain.repository.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(private val firebaseDataSource: FirebaseDataSource) :
    FirebaseRepository {
    override suspend fun signInUser(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> = firebaseDataSource.signInUser(email, password)

    override suspend fun registerUser(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> = firebaseDataSource.registerUser(email, password)

    override suspend fun saveUserData(user: User): Flow<Resource<Boolean>> = firebaseDataSource.saveUserData(user)

    override suspend fun saveUserPhoto(imageUri: Uri): Flow<Resource<String>> = firebaseDataSource.saveUserPhoto(imageUri)

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseDataSource.getCurrentUser()
    }

    override fun logoutUser() {
        return firebaseDataSource.logoutUser()
    }
}