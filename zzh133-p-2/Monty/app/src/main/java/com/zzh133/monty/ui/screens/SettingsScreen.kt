package com.zzh133.monty.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.zzh133.monty.ui.HomeVM
import com.zzh133.monty.ui.theme.MontyTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


@Composable
fun SettingsScreen(navController: NavController,homeVM: HomeVM = viewModel()) {

    val selectedCardCount = homeVM.selectedCardCount

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Title
        Text(
            text = "Game Settings",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Card Count Selection Text
        Text(
            text = "Select Number of Cards:",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Card Count Radio Buttons
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 3-Card Mode
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = selectedCardCount.value == 3,
                    onClick = { selectedCardCount.value = 3 }
                )
                Text("3-Card Mode", modifier = Modifier.padding(start = 8.dp))
            }

            // 4-Card Mode
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = selectedCardCount.value == 4,
                    onClick = { selectedCardCount.value = 4 }
                )
                Text("4-Card Mode", modifier = Modifier.padding(start = 8.dp))
            }

            // 5-Card Mode
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = selectedCardCount.value == 5,
                    onClick = { selectedCardCount.value = 5 }
                )
                Text("5-Card Mode", modifier = Modifier.padding(start = 8.dp))
            }
        }

        // Button Row - Save and Back Homepage
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // save button
                val context = LocalContext.current

                Button(
                    onClick = {
                        Toast.makeText(
                            context,
                            "Card mode saved: ${selectedCardCount.value} cards",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate("home")
                    },
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text("Save Mode")
                }

                // return button
                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text("Homepage")
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "App Theme",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Dark Theme",
                fontSize = 16.sp,
            )

            Switch(
                checked = homeVM.isDarkTheme.value,
                onCheckedChange = {
                    homeVM.isDarkTheme.value = it
                }
            )
        }
    }
}

// Preview the Settings Screen
@Preview(
    showBackground = true,
    widthDp = 640,
    heightDp = 360,
    name = "Landscape Preview"
)
@Composable
fun SettingsScreenPreview() {
    MontyTheme {
        SettingsScreen(navController = NavController(context = LocalContext.current))
    }
}