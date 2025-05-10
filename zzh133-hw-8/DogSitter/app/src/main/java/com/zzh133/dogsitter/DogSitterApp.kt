package com.zzh133.dogsitter


import android.app.Application
import android.util.Log
import com.zzh133.dogsitter.data.DogSitterDatabase
import com.zzh133.dogsitter.data.DogSitterRepository

class DogSitterApp : Application() {

    // 日志测试
    override fun onCreate() {
        super.onCreate()
        Log.d("DogSitterApp", "~~~\nDogSitterApp\nonCreate\ntest\n~~~")
    }

    // 全局 Repository 提供给 Factory 使用
    val database by lazy { DogSitterDatabase.getDatabase(this) }
    val repository by lazy { DogSitterRepository(database.dogDao(), database.appointmentDao()) }
}
