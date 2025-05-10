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
        // 1. 插入数据
        val testDog = Dog(name = "Buddy", ownerName = "Alice", breed = "Labrador", notes = "Friendly")
        val testAppointment = Appointment(startTime = 123456789L, duration = 60L, location = "Central Park", notes = "Morning walk")

        dogDao.insert(testDog)
        appointmentDao.insert(testAppointment)

        // 2. 获取数据
        val dogs = dogDao.getAllDogs().first()
        val appts = appointmentDao.getAllAppointments().first()

        // 3. 打印到 logcat
        Log.d("Test", "Dog: ${dogs.firstOrNull()}")
        Log.d("Test", "Appointment: ${appts.firstOrNull()}")

        // 4. 断言匹配
        assertEquals("Buddy", dogs.firstOrNull()?.name)
        assertEquals("Central Park", appts.firstOrNull()?.location)
    }
}
