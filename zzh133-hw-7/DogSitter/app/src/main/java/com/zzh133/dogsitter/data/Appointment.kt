package com.zzh133.dogsitter.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Appointment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startTime: Long,
    val duration: Long,
    val location: String,
    val notes: String
)
