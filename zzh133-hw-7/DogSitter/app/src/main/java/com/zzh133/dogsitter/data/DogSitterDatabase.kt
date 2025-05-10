package com.zzh133.dogsitter.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Dog::class, Appointment::class], version = 1)
abstract class DogSitterDatabase : RoomDatabase() {
    abstract fun dogDao(): DogDao
    abstract fun appointmentDao(): AppointmentDao

    companion object {
        @Volatile
        private var INSTANCE: DogSitterDatabase? = null

        fun getDatabase(context: Context): DogSitterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DogSitterDatabase::class.java,
                    "dogsitter_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
