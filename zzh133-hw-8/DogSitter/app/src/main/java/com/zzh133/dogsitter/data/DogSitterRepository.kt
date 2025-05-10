package com.zzh133.dogsitter.data

import kotlinx.coroutines.flow.Flow


class DogSitterRepository(
    private val dogDao: DogDao,
    private val appointmentDao: AppointmentDao
) {
    val allDogs: Flow<List<Dog>> = dogDao.getAllDogs()
    val allAppointments: Flow<List<Appointment>> = appointmentDao.getAllAppointments()   // ← 编译恢复

    suspend fun insertDog(dog: Dog) = dogDao.insert(dog)
    suspend fun insertAppointment(appointment: Appointment) = appointmentDao.insert(appointment)

    fun getAppointmentsForDog(dogId: Int): Flow<List<Appointment>> =
        appointmentDao.getAppointmentsForDog(dogId)

    suspend fun deleteAppointment(id: Int) = appointmentDao.deleteById(id)
}
