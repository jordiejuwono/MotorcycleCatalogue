package com.jordju.core.data.remote

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jordju.core.data.Resource
import com.jordju.core.data.model.User
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

interface FirebaseDataSource {
    suspend fun signInUser(email: String, password: String): Flow<Resource<FirebaseUser?>>
    suspend fun registerUser(email: String, password: String): Flow<Resource<FirebaseUser?>>
    suspend fun saveUserData(user: User): Flow<Resource<Boolean>>
    suspend fun saveUserPhoto(imageUri: Uri): Flow<Resource<String>>
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
                    if (it.isSuccessful) {
                        trySend(Resource.Success(it.result.user))
                    } else {
                        trySend(Resource.Error(it.exception?.message))
                    }
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
                    if (it.isSuccessful) {
                        trySend(Resource.Success(it.result.user))
                    } else {
                        trySend(Resource.Error(it.exception?.message))
                    }
                }.addOnFailureListener {
                    trySend(Resource.Error(it.message))
                }

            awaitClose { this.cancel() }
        }
    }

    override suspend fun saveUserData(user: User): Flow<Resource<Boolean>> {
        return callbackFlow {
            trySend(Resource.Loading())
            FirebaseFirestore.getInstance().collection("users").add(user).addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Resource.Success(true))
                } else {
                    trySend(Resource.Error(it.exception?.message))
                }
            }.addOnFailureListener {
                trySend(Resource.Error(it.message))
            }

            awaitClose { this.cancel() }
        }
    }

    override suspend fun saveUserPhoto(imageUri: Uri): Flow<Resource<String>> {
        return callbackFlow {
            trySend(Resource.Loading())
            FirebaseStorage.getInstance().getReference("users/${auth.currentUser?.uid}")
                .putFile(imageUri).addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(Resource.Success(it.result.storage.path))
                    } else {
                        trySend(Resource.Error(it.exception?.message))
                    }
                }
                .addOnFailureListener {
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