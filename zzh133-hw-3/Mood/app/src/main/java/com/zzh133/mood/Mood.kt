package com.zzh133.mood

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class Mood : ViewModel() {

    var moodImageID by mutableStateOf(R.drawable.happyhappy)
        private set

    var moodTextID by mutableStateOf(R.string.quote_01)
        private set

    fun clickHappy(){

        moodImageID = R.drawable.happyhappy
        moodTextID = R.string.quote_01
    }


    fun clickSad(){
        moodImageID = R.drawable.sadsad
        moodTextID = R.string.quote_03
    }

    fun clickSoso(){
        moodImageID = R.drawable.sosososo
        moodTextID = R.string.quote_02
    }

}