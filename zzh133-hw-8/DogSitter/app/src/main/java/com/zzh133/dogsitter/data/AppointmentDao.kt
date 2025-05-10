package com.zzh133.dogsitter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {

    @Insert
    suspend fun insert(appointment: Appointment): Long


    @Query("SELECT * FROM Appointment")
    fun getAllAppointments(): Flow<List<Appointment>>


    @Query("SELECT * FROM Appointment WHERE dogId = :dogId")
    fun getAppointmentsForDog(dogId: Int): Flow<List<Appointment>>


    @Query("DELETE FROM Appointment WHERE id = :id")
    suspend fun deleteById(id: Int)

}
