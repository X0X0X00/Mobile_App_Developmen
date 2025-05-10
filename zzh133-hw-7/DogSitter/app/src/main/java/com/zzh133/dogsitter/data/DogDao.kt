package com.zzh133.dogsitter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {
    @Insert
    suspend fun insert(dog: Dog)

    @Query("SELECT * FROM Dog")
    fun getAllDogs(): Flow<List<Dog>>
}
