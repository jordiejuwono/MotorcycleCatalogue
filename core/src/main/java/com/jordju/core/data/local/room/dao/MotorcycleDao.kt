package com.jordju.core.data.local.room.dao

import androidx.room.*
import com.jordju.core.data.local.room.entity.MotorcycleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MotorcycleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMotorcycles(motorcycles: List<MotorcycleEntity>)

    @Query("SELECT * FROM motorcycle")
    fun getAllMotorcycles(): Flow<List<MotorcycleEntity>>

    @Query("UPDATE motorcycle SET is_favorite = :setToFavorite WHERE motorcycle_id = :motorcycleId")
    suspend fun setMotorcycleFavoriteStatus(motorcycleId: Int, setToFavorite: Boolean)

    @Query("SELECT * FROM motorcycle WHERE is_favorite = true")
    fun getAllFavoriteMotorcycles(): Flow<List<MotorcycleEntity>>

    @Delete
    suspend fun deleteMotorcycleFromWishlist(motorcycle: MotorcycleEntity)

    @Query("SELECT EXISTS(SELECT * FROM motorcycle WHERE motorcycle_id=:id)")
    fun isMotorcycleAlreadyExists(id: Int): Flow<Boolean>

}