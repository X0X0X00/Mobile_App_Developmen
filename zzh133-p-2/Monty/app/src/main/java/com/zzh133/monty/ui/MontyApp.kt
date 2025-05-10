package com.zzh133.monty.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zzh133.monty.ui.screens.BankScreen
import com.zzh133.monty.ui.screens.HomeScreen
import com.zzh133.monty.ui.screens.SettingsScreen
import com.zzh133.monty.GameScreen
import com.zzh133.monty.ui.theme.MontyTheme

@Composable
fun MontyApp() {

    val navController: NavHostController = rememberNavController()
    val homeVM: HomeVM = viewModel()

    MontyTheme(darkTheme = homeVM.isDarkTheme.value) {
        Surface(color = MaterialTheme.colorScheme.background) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeScreen(navController) }
                composable("game") { GameScreen(navController, homeVM) }
                composable("settings") { SettingsScreen(navController, homeVM) }
                composable("bank") { BankScreen(navController, homeVM) }
            }
        }
    }
}
