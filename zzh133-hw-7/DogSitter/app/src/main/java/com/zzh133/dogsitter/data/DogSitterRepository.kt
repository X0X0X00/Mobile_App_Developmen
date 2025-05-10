package com.zzh133.dogsitter.data

import kotlinx.coroutines.flow.Flow


class DogSitterRepository(
    private val dogDao: DogDao,
    private val appointmentDao: AppointmentDao
) {
    val allDogs: Flow<List<Dog>> = dogDao.getAllDogs()
    val allAppointments: Flow<List<Appointment>> = appointmentDao.getAllAppointments()

    suspend fun insertDog(dog: Dog) = dogDao.insert(dog)
    suspend fun insertAppointment(appointment: Appointment) = appointmentDao.insert(appointment)
}
