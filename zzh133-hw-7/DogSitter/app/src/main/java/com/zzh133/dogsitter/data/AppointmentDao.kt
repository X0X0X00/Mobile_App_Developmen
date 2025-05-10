package com.zzh133.dogsitter.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    @Insert
    suspend fun insert(appointment: Appointment)

    @Query("SELECT * FROM Appointment")
    fun getAllAppointments(): Flow<List<Appointment>>
}