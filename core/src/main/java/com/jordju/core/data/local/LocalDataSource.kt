package com.jordju.core.data.local

import com.jordju.core.data.local.entity.MotorcycleEntity
import com.jordju.core.data.local.room.MotorcycleDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalDataSource {

    fun getAllMotorcycles(): Flow<List<MotorcycleEntity>>
    suspend fun insertMotorcycleToWishlist(motorcycle: MotorcycleEntity)
    suspend fun deleteMotorcycleFromWishlist(motorcycle: MotorcycleEntity)
    fun isMotorcycleAlreadyExists(id: Int): Flow<Boolean>

}

class LocalDataSourceImpl @Inject constructor(private val motorcycleDao: MotorcycleDao) :
    LocalDataSource {

    override fun getAllMotorcycles(): Flow<List<MotorcycleEntity>> =
        motorcycleDao.getAllMotorcycles()

    override suspend fun insertMotorcycleToWishlist(motorcycle: MotorcycleEntity) =
        motorcycleDao.insertMotorcycleToWishlist(motorcycle)

    override suspend fun deleteMotorcycleFromWishlist(motorcycle: MotorcycleEntity) =
        motorcycleDao.deleteMotorcycleFromWishlist(motorcycle)

    override fun isMotorcycleAlreadyExists(id: Int): Flow<Boolean> =
        motorcycleDao.isMotorcycleAlreadyExists(id)

}