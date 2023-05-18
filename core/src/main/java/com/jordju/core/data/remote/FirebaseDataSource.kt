package com.jordju.core.data.remote

import android.net.Uri
import android.os.Message
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.RemoteMessage.Notification
import com.google.firebase.storage.FirebaseStorage
import com.jordju.core.data.Resource
import com.jordju.core.data.model.MotorcycleOrderDetails
import com.jordju.core.data.model.User
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID
import javax.inject.Inject

interface FirebaseDataSource {
    suspend fun signInUser(email: String, password: String): Flow<Resource<FirebaseUser?>>
    suspend fun registerUser(email: String, password: String): Flow<Resource<FirebaseUser?>>
    suspend fun saveUserData(userReference: String, user: User): Flow<Resource<Boolean>>
    suspend fun saveUserPhoto(userUid: String, imageUri: Uri): Flow<Resource<String>>
    suspend fun fetchUserPhoto(userUid: String): Flow<Resource<Uri>>
    suspend fun getUserFullData(): Flow<Resource<User?>>
    suspend fun sendMotorcycleOrder(
        userReference: String,
        motorcycle: MotorcycleOrderDetails
    ): Flow<Resource<Boolean>>
    suspend fun getMotorcyclesOrder(userReference: String): Flow<Resource<List<MotorcycleOrderDetails>>>
    suspend fun cancelMotorcycleOrder(userReference: String, orderId: String): Flow<Resource<Boolean>>
    suspend fun fetchFirebaseMessagingToken(): Flow<Resource<String>>
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

    override suspend fun fetchUserPhoto(userUid: String): Flow<Resource<Uri>> {
        return callbackFlow {
            trySend(Resource.Loading())
            FirebaseStorage.getInstance().getReference("users/$userUid").downloadUrl
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(Resource.Success(it.result))
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

    override suspend fun sendMotorcycleOrder(
        userReference: String,
        motorcycle: MotorcycleOrderDetails
    ): Flow<Resource<Boolean>> {
        return callbackFlow {
            trySend(Resource.Loading())
            FirebaseFirestore.getInstance().collection("orders")
                .document(userReference)
                .collection("orderList")
                .document(motorcycle.uuid)
                .set(motorcycle)
                .addOnCompleteListener { reference ->
                    if (reference.isSuccessful) {
                        trySend(Resource.Success(true))
                    } else {
                        trySend(Resource.Error(reference.exception?.message))
                    }
                }.addOnFailureListener {
                    trySend(Resource.Error(it.message))
                }

            awaitClose { this.cancel() }
        }
    }

    override suspend fun getMotorcyclesOrder(
        userReference: String,
    ): Flow<Resource<List<MotorcycleOrderDetails>>> {
        return callbackFlow {
            trySend(Resource.Loading())
            FirebaseFirestore.getInstance().collection("orders")
                .document(userReference)
                .collection("orderList")
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(Resource.Success(it.result.toObjects(MotorcycleOrderDetails::class.java)))
                    } else {
                        trySend(Resource.Error(it.exception?.message))
                    }
                }.addOnFailureListener {
                    trySend(Resource.Error(it.message))
                }

            awaitClose { this.cancel() }
        }
    }

    override suspend fun cancelMotorcycleOrder(userReference: String, orderId: String): Flow<Resource<Boolean>> {
        return callbackFlow {
            trySend(Resource.Loading())
            FirebaseFirestore.getInstance().collection("orders")
                .document(userReference)
                .collection("orderList")
                .document(orderId)
                .delete()
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

    override suspend fun fetchFirebaseMessagingToken(): Flow<Resource<String>> {
        return callbackFlow {
            trySend(Resource.Loading())
            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Resource.Success(it.result))
                } else {
                    trySend(Resource.Error(it.exception?.message))
                }
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