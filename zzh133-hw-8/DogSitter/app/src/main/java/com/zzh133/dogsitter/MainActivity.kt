package com.zzh133.dogsitter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zzh133.dogsitter.ui.screens.AppointmentsScreen
import com.zzh133.dogsitter.ui.screens.InfoScreen
import com.zzh133.dogsitter.ui.screens.SettingsScreen
import com.zzh133.dogsitter.ui.theme.DogSitterTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem

class MainActivity : ComponentActivity() {

    private val viewModel: DogSitterViewModel by viewModels {
        DogSitterViewModelFactory((application as DogSitterApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { DogSitterTheme { MainScaffold(viewModel) } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(viewModel: DogSitterViewModel) {

    /** 0‑Appointments  1‑Info  2‑Settings */
    var currentTab by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("DogSitter") }) },

        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, null) },
                    label = { Text("Appointments") },
                    selected = currentTab == 0,
                    onClick = { currentTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, null) },
                    label = { Text("Info") },
                    selected = currentTab == 1,
                    onClick = { currentTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, null) },
                    label = { Text("Settings") },
                    selected = currentTab == 2,
                    onClick = { currentTab = 2 }
                )
            }
        },
    ) { inner ->
        Box(Modifier.padding(inner)) {
            when (currentTab) {
                0 -> AppointmentsScreen(viewModel)
                1 -> InfoScreen()
                2 -> SettingsScreen()
            }
        }
    }
}




