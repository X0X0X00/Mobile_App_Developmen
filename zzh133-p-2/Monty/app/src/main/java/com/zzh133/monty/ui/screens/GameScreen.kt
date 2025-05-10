package com.zzh133.monty

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zzh133.monty.ui.HomeVM
import com.zzh133.monty.ui.theme.MontyTheme
import com.zzh133.monty.ui.data.card
import com.zzh133.monty.ui.data.generateGameCards
import com.zzh133.monty.ui.data.GameRecord
import kotlinx.coroutines.delay
import com.zzh133.monty.R
import kotlinx.coroutines.launch

@Composable
fun GameScreen(navController: NavController, homeVM: HomeVM = viewModel()) {
    val cardCount = homeVM.selectedCardCount.value
    var gameCards by remember { mutableStateOf(emptyList<card>()) }

    LaunchedEffect(cardCount) {
        gameCards = generateGameCards(cardCount)
    }

    val playerBalance = homeVM.playerBalance
    val bankBalance = homeVM.bankBalance
    val gameHistory = homeVM.gameHistory

    val playerDisplay = remember(playerBalance.value) { "%.2f".format(playerBalance.value) }
    val bankDisplay = remember(bankBalance.value) { "%.2f".format(bankBalance.value) }

    // 动态计算赌注金额
    val betAmount = remember(cardCount) {
        when (cardCount) {
            3 -> 30.0
            4 -> 40.0
            5 -> 50.0
            else -> 50.0
        }
    }

    var gameResult by remember { mutableStateOf<String?>(null) }
    var cardRevealed by remember { mutableStateOf(false) }
    var overlayResult by remember { mutableStateOf<String?>(null) }


    Box(modifier = Modifier.fillMaxSize()) {

        // 游戏主界面
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            Spacer(modifier = Modifier.height(20.dp))
            // Balance info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Player Balance: $$playerDisplay",
                    fontSize = 16.sp,
                    color = if (playerBalance.value > 0) Color.Green else Color.Red
                )
                Text(
                    text = "Bank Balance: $$bankDisplay",
                    fontSize = 16.sp,
                    color = Color.Blue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Current Bet: $${"%.2f".format(betAmount)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Card display
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                gameCards.forEachIndexed { index, card ->

                    val scale by animateFloatAsState(targetValue = if (card.isFaceUp) 1.1f else 1f)

                    Card(
                        modifier = Modifier
                            .graphicsLayer(
                                scaleX = scale,
                                scaleY = scale
                            )
                            .size(100.dp, 150.dp)
                            .clickable(enabled = !card.isFaceUp && !cardRevealed) {
                                if (!cardRevealed) {
                                    val updatedCards = gameCards.toMutableList()
                                    val selectedCard = card.copy(isFaceUp = true)
                                    updatedCards[index] = selectedCard
                                    gameCards = updatedCards
                                    cardRevealed = true

                                    if (selectedCard.isWinningCard) {
                                        playerBalance.value += betAmount
                                        bankBalance.value -= betAmount
                                        gameHistory.add(GameRecord(cardCount, betAmount))
                                        gameResult = "You Won $${"%.2f".format(betAmount)}"
                                        overlayResult = "win"
                                    } else {
                                        playerBalance.value -= betAmount
                                        bankBalance.value += betAmount
                                        gameHistory.add(GameRecord(cardCount, -betAmount))
                                        gameResult = "You Lost $${"%.2f".format(betAmount)}"
                                        overlayResult = "lose"
                                    }
                                }
                            }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            if(!card.isFaceUp) {
                                Image(
                                    painter = painterResource(id = R.drawable._poker),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                )
                            }
                            if (card.isFaceUp) {
                                Text(
                                    text = "${card.value}${card.suit}",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Game result
            gameResult?.let { result ->
                Text(
                    text = result,
                    color = if (result.contains("Won")) Color.Green else Color.Red,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Action buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.wrapContentWidth()
            ) {
                if(gameResult?.contains("Won") == true || gameResult?.contains("Lost") == true){
                    Button(
                    onClick = {
                        gameCards = generateGameCards(cardCount)
                        gameResult = null
                        cardRevealed = false
                    },
                    modifier = Modifier.wrapContentWidth()
                    ) {
                        Text("Play Again")
                    }
                }

                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text("Homepage")
                }
            }
        }

        // 显示 Win / Lose 图片
        if (overlayResult != null) {
            val imageRes = if (overlayResult == "win") R.drawable.win else R.drawable.lose

            val rotation = remember { Animatable(0f) }
            val alpha = remember { Animatable(0f) }

            LaunchedEffect(overlayResult) {
                rotation.snapTo(0f)
                alpha.snapTo(0f)

                // 同时执行动画
                launch { rotation.animateTo(360f) }
                launch { alpha.animateTo(1f) }

                delay(1500)

                // 淡出
                alpha.animateTo(0f)
                overlayResult = null
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = overlayResult,
                    modifier = Modifier
                        .size(300.dp)
                        .graphicsLayer(
                            rotationZ = rotation.value,
                            alpha = alpha.value
                        )
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 640,
    heightDp = 360,
    name = "Landscape Preview"
)
@Composable
fun GameScreenPreview() {
    MontyTheme {
        val navController = rememberNavController()
        val dummyHomeVM = HomeVM()
        GameScreen(navController = navController, homeVM = dummyHomeVM)
    }
}
