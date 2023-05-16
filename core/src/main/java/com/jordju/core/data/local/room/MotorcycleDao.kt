package com.jordju.core.data.local.room

import androidx.room.*
import com.jordju.core.data.local.entity.MotorcycleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MotorcycleDao {

    @Query("SELECT * FROM motorcycle")
    fun getAllMotorcycles(): Flow<List<MotorcycleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMotorcycleToWishlist(motorcycle: MotorcycleEntity)

    @Delete
    suspend fun deleteMotorcycleFromWishlist(motorcycle: MotorcycleEntity)

    @Query("SELECT EXISTS(SELECT * FROM motorcycle WHERE motorcycle_id=:id")
    fun isMotorcycleAlreadyExists(id: Int): Flow<Boolean>

}