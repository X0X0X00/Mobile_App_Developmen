package com.zzh133.dogsitter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzh133.dogsitter.data.Appointment
import com.zzh133.dogsitter.data.Dog
import com.zzh133.dogsitter.data.DogSitterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DogSitterViewModel(
    private val repository: DogSitterRepository
) : ViewModel() {

    // 所有狗狗的数据
    val allDogs: Flow<List<Dog>> = repository.allDogs

    // 所有预约的数据
    val allAppointments: Flow<List<Appointment>> = repository.allAppointments

    // 添加狗狗
    fun addDog(dog: Dog) {
        viewModelScope.launch {
            repository.insertDog(dog)
        }
    }

    // 添加预约
    fun addAppointment(appointment: Appointment) {
        viewModelScope.launch {
            repository.insertAppointment(appointment)
        }
    }
}
