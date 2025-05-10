package com.zzh133.monty.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.zzh133.monty.ui.data.GameRecord


class HomeVM : ViewModel() {
    val selectedCardCount = mutableStateOf(3)
    val playerBalance = mutableStateOf(1000.0)
    val bankBalance = mutableStateOf(5000.0)
    val gameHistory = mutableStateListOf<GameRecord>()
    val isDarkTheme = mutableStateOf(false)

}