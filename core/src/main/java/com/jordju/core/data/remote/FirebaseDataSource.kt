package com.jordju.core.data.remote

import android.net.Uri
import android.util.Log
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
    suspend fun saveUserData(userReference: String, user: User): Flow<Resource<Boolean>>
    suspend fun saveUserPhoto(userUid: String, imageUri: Uri): Flow<Resource<String>>
    suspend fun getUserFullData(): Flow<Resource<User?>>
//    fun getUserProfilePicture(): String
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

    override suspend fun saveUserData(userReference: String, user: User): Flow<Resource<Boolean>> {
        return callbackFlow {
            trySend(Resource.Loading())
            FirebaseFirestore.getInstance().collection("users")
                .document(userReference)
                .set(user)
                .addOnCompleteListener {
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

    override suspend fun saveUserPhoto(userUid: String, imageUri: Uri): Flow<Resource<String>> {
        return callbackFlow {
            trySend(Resource.Loading())
            FirebaseStorage.getInstance().getReference("users/$userUid")
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

    override suspend fun getUserFullData(): Flow<Resource<User?>> {
        return callbackFlow {
            trySend(Resource.Loading())
            FirebaseFirestore.getInstance().collection("users")
                .document(auth.currentUser?.uid ?: "").get()
                .addOnSuccessListener { document ->
                    try {
                        Log.d("TEST TAG", "getUserFullData: ${document.data}")
                        Log.d("TEST TAG", "getUserFullData: ${document}")
                        trySend(Resource.Success(document.toObject(User::class.java)))
                    } catch (e: Exception) {
                        trySend(Resource.Error(e.message.orEmpty()))
                    }
                }
                .addOnFailureListener {
                    trySend(Resource.Error(it.message))
                }

            awaitClose { this.cancel() }
        }
    }

//    override suspend fun getUserProfilePicture(): Flow<Resource<String>> {
//        return callbackFlow {
//            trySend(Resource.Loading())
//            FirebaseStorage.getInstance().getReference("users/${auth.currentUser?.uid}").downloadUrl.result
//                .putFile(imageUri).addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        trySend(Resource.Success(it.result.storage.path))
//                    } else {
//                        trySend(Resource.Error(it.exception?.message))
//                    }
//                }
//                .addOnFailureListener {
//                    trySend(Resource.Error(it.message))
//                }
//
//            awaitClose { this.cancel() }
//        }
//    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun logoutUser() {
        return auth.signOut()
    }

}