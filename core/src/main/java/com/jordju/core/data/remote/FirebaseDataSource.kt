package com.jordju.core.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jordju.core.data.Resource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

interface FirebaseDataSource {
    suspend fun signInUser(email: String, password: String): Flow<Resource<FirebaseUser?>>
    suspend fun registerUser(email: String, password: String): Flow<Resource<FirebaseUser?>>
    fun getCurrentUser(): FirebaseUser?
    fun logoutUser()
}

class FirebaseDataSourceImpl @Inject constructor(private val auth: FirebaseAuth) :
    FirebaseDataSource {
    override suspend fun signInUser(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> {
        return callbackFlow {
            trySend(Resource.Loading())
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    trySend(Resource.Success(it.result.user))
                }.addOnFailureListener {
                    trySend(Resource.Error(it.message))
                }

            awaitClose { this.cancel() }
        }
    }

    override suspend fun registerUser(
        email: String,
        password: String
    ): Flow<Resource<FirebaseUser?>> {
        return callbackFlow {
            trySend(Resource.Loading())
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    trySend(Resource.Success(it.result.user))
                }.addOnFailureListener {
                    trySend(Resource.Error(it.message))
                }

            awaitClose { this.cancel() }
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun logoutUser() {
        return auth.signOut()
    }

}