package com.zzh133.dogsitter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {

    // 插入一只狗狗，返回插入的主键（id）
    @Insert
    suspend fun insert(dog: Dog): Long

    // 获取所有狗狗
    @Query("SELECT * FROM Dog")
    fun getAllDogs(): Flow<List<Dog>>

    // （可选）根据 id 获取一只狗
    @Query("SELECT * FROM Dog WHERE id = :id")
    suspend fun getDogById(id: Int): Dog
}
