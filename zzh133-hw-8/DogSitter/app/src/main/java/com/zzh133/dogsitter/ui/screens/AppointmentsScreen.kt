package com.zzh133.dogsitter.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zzh133.dogsitter.DogSitterViewModel
import com.zzh133.dogsitter.data.Appointment
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import kotlinx.coroutines.launch

@Composable
fun AppointmentsScreen(viewModel: DogSitterViewModel) {
    var showDialog by remember { mutableStateOf(false) }

    var dogIdText     by remember { mutableStateOf("") }
    var startTimeText by remember { mutableStateOf("") }
    var durationText  by remember { mutableStateOf("") }
    var locationText  by remember { mutableStateOf("") }
    var ownerText     by remember { mutableStateOf("") }

    val appointments by viewModel.allAppointments.collectAsState(initial = emptyList())

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(appointments, key = { it.id }) { appt ->
                    AppointmentRow(
                        appt = appt,
                        onDelete = { viewModel.deleteAppointment(appt.id) }
                    )
                    Divider()
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            val dogId = dogIdText.toIntOrNull() ?: return@TextButton
                            val startTime = startTimeText.toLongOrNull() ?: return@TextButton
                            val duration = durationText.toLongOrNull() ?: return@TextButton

                            viewModel.addAppointment(
                                Appointment(
                                    dogId = dogId,
                                    startTime = startTime,
                                    duration = duration,
                                    location = locationText,
                                    owner = ownerText
                                )
                            )

                            // 清空输入
                            dogIdText = ""; startTimeText = ""; durationText = ""
                            locationText = ""; ownerText = ""
                            showDialog = false

                            // 显示 snackbar
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Saved")
                            }



                            // custom feature

                        }) {
                            Text("Save")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                    },
                    title = { Text("New Appointment") },
                    text = {
                        Column {
                            OutlinedTextField(dogIdText, { dogIdText = it }, label = { Text("Dog ID") })
                            OutlinedTextField(startTimeText, { startTimeText = it }, label = { Text("Start Time (ms)") })
                            OutlinedTextField(durationText, { durationText = it }, label = { Text("Duration (min)") })
                            OutlinedTextField(locationText, { locationText = it }, label = { Text("Location") })
                            OutlinedTextField(ownerText, { ownerText = it }, label = { Text("Owner") })
                        }
                    }
                )
            }
        }
    }
}


@Composable
private fun AppointmentRow(
    appt: Appointment,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text("Dog ID: ${appt.dogId}", style = MaterialTheme.typography.bodyLarge)
            Text("${appt.location} • ${appt.duration} min", style = MaterialTheme.typography.bodyMedium)
            Text("Owner: ${appt.owner}", style = MaterialTheme.typography.bodySmall)
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}
