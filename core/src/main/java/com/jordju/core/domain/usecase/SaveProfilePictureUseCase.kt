package com.jordju.core.domain.usecase

import android.net.Uri
import com.jordju.core.data.Resource
import com.jordju.core.domain.repository.MotorcycleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveProfilePictureUseCase @Inject constructor(private val repository: MotorcycleRepository) {

    suspend fun execute(userUid: String, uriFile: Uri): Flow<Resource<String>> =
        repository.saveUserPhoto(userUid, uriFile)

}