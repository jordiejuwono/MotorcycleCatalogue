package com.jordju.core.data.local

import com.jordju.core.data.local.room.entity.MotorcycleEntity
import com.jordju.core.data.local.room.dao.MotorcycleDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalDataSource {

    fun getAllMotorcycles(): Flow<List<MotorcycleEntity>>
    suspend fun deleteMotorcycleFromWishlist(motorcycle: MotorcycleEntity)
    fun isMotorcycleAlreadyExists(id: Int): Flow<Boolean>
    suspend fun setMotorcycleFavoriteStatus(motorcycleId: Int, setToFavorite: Boolean)
    suspend fun getAllFavoriteMotorcycles(): Flow<List<MotorcycleEntity>>

}

class LocalDataSourceImpl @Inject constructor(private val motorcycleDao: MotorcycleDao) :
    LocalDataSource {

    override fun getAllMotorcycles(): Flow<List<MotorcycleEntity>> =
        motorcycleDao.getAllMotorcycles()

    override suspend fun deleteMotorcycleFromWishlist(motorcycle: MotorcycleEntity) =
        motorcycleDao.deleteMotorcycleFromWishlist(motorcycle)

    override fun isMotorcycleAlreadyExists(id: Int): Flow<Boolean> =
        motorcycleDao.isMotorcycleAlreadyExists(id)

    override suspend fun setMotorcycleFavoriteStatus(motorcycleId: Int, setToFavorite: Boolean) =
        motorcycleDao.setMotorcycleFavoriteStatus(motorcycleId, setToFavorite)

    override suspend fun getAllFavoriteMotorcycles(): Flow<List<MotorcycleEntity>> =
        motorcycleDao.getAllFavoriteMotorcycles()

}