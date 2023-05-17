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
    suspend fun getUserFullData(): Flow<Resource<User?>>
    suspend fun sendMotorcycleOrder(
        userReference: String,
        motorcycle: MotorcycleOrderDetails
    ): Flow<Resource<String>>
    suspend fun getMotorcyclesOrder(userReference: String): Flow<Resource<List<MotorcycleOrderDetails>>>
    suspend fun cancelMotorcycleOrder(): Flow<Resource<Boolean>>
    suspend fun fetchFirebaseMessagingToken(): Flow<Resource<String>>
    suspend fun subscribeToTopic(uid: String): Flow<Resource<Boolean>>
    suspend fun sendDataToTopic(
        uid: String,
        title: String,
        message: String
    ): Flow<Resource<Boolean>>

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
    ): Flow<Resource<String>> {
        return callbackFlow {
            trySend(Resource.Loading())
            FirebaseFirestore.getInstance().collection("orders")
                .document(userReference)
                .collection("orderList")
                .add(motorcycle)
                .addOnCompleteListener { reference ->
                    if (reference.isSuccessful) {
                        trySend(Resource.Success(reference.result.id))
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

    override suspend fun cancelMotorcycleOrder(): Flow<Resource<Boolean>> {
        TODO("Not yet implemented")
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

    override suspend fun subscribeToTopic(uid: String): Flow<Resource<Boolean>> {
        return callbackFlow {
            trySend(Resource.Loading())
            FirebaseMessaging.getInstance().subscribeToTopic(uid).addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(Resource.Success(true))
                } else {
                    trySend(Resource.Error(it.exception?.message))
                }
            }

            awaitClose { this.cancel() }
        }
    }

    override suspend fun sendDataToTopic(
        uid: String,
        title: String,
        message: String
    ): Flow<Resource<Boolean>> {
        return callbackFlow {
            trySend(Resource.Loading())
            val remoteMessage = RemoteMessage.Builder(uid)
                .addData("title", title)
                .addData("body", message)
                .build()
            FirebaseMessaging.getInstance().send(remoteMessage)

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