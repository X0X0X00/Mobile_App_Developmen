package com.zzh133.dogsitter

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.zzh133.dogsitter.data.Appointment
import com.zzh133.dogsitter.data.AppointmentDao
import com.zzh133.dogsitter.data.Dog
import com.zzh133.dogsitter.data.DogDao
import com.zzh133.dogsitter.data.DogSitterDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DogSitterTest {

    private lateinit var database: DogSitterDatabase
    private lateinit var dogDao: DogDao
    private lateinit var appointmentDao: AppointmentDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            DogSitterDatabase::class.java
        ).allowMainThreadQueries().build()

        dogDao = database.dogDao()
        appointmentDao = database.appointmentDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testInsertAndRetrieve() = runBlocking {
        val testDog =
            Dog(name = "Buddy", ownerName = "Alice", breed = "Labrador", notes = "Friendly")
        val dogId = dogDao.insert(testDog).toInt()

        val testAppointment = Appointment(
            dogId = dogId,
            startTime = 123456789L,
            duration = 60L,
            location = "Central Park",
            owner = "Morning walk"
        )

        appointmentDao.insert(testAppointment)          // 只插一次 dog、一次 appointment

        // 全部狗
        val dogs = dogDao.getAllDogs().first()
        // 指定狗的预约，用新方法
        val appts = appointmentDao.getAppointmentsForDog(dogId).first()

        assertEquals("Buddy", dogs.first().name)
        assertEquals("Central Park", appts.first().location)
    }
}