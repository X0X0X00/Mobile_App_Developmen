package com.zzh133.dogsitter.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val ownerName: String,
    val breed: String,
    val notes: String
)
