package com.zzh133.dogsitter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zzh133.dogsitter.data.DogSitterRepository

class DogSitterViewModelFactory(
    private val repository: DogSitterRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DogSitterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DogSitterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

