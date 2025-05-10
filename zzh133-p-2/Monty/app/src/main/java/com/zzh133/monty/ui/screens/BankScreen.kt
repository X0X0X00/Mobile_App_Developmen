package com.zzh133.monty.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.zzh133.monty.ui.HomeVM
import com.zzh133.monty.ui.theme.MontyTheme

// Game record data class
data class GameRecord(
    val gameType: Int,  // 3, 4, or 5 cards
    val amount: Double  // Win/loss amount
)

@Composable
fun BankScreen(navController: NavController, homeVM: HomeVM = viewModel()) {
    // Sample game history and account balances
    val playerBalance = homeVM.playerBalance.value
    val bankBalance = homeVM.bankBalance.value
    val gameHistory = homeVM.gameHistory


    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { navController.navigate("home") }) {
                    Text("Homepage")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Bank Account",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Player Balance: ¥${"%.2f".format(playerBalance)}",
                    fontSize = 18.sp
                )
                Text(
                    text = "Bank Balance: ¥${"%.2f".format(bankBalance)}",
                    fontSize = 18.sp
                )
            }

            Text(
                text = "Game History",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("No.", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                        Text(
                            "Game Type",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        Text("Amount", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    }
                }

                itemsIndexed(gameHistory) { index, record ->

                    var expanded by remember { mutableStateOf(false) }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize()
                            .clickable { expanded = !expanded }
                            .padding(vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text((index + 1).toString(), modifier = Modifier.weight(1f))
                            Text("${record.gameType}-Card", modifier = Modifier.weight(1f))
                            Text(
                                "¥${"%.2f".format(record.amount)}",
                                color = if (record.amount >= 0) Color.Green else Color.Red,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        if (expanded) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (record.amount >= 0) "You won this game!" else "You lost this game.",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }

            }
        }
    }
}


// Preview
@Preview(
    showBackground = true,
    widthDp = 640,
    heightDp = 360,
    name = "Landscape Preview"
)
@Composable
fun BankScreenPreview() {
    MontyTheme {
        BankScreen(navController = NavController(context = LocalContext.current))

    }
}
