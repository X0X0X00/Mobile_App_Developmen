package com.csc.chickentap.ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.csc.chickentap.model.ChickenTap
import com.csc.chickentap.ui.theme.ChickenTapTheme
import com.csc.chickentap.ui.views.gameview.GameView
import com.csc.chickentap.ui.views.gameview.GameViewVM


object Route {
    const val GAME = "Game"
    const val LEADERBOARD = "Leaderboard"
}

@Composable
fun HomeView(viewModel: GameViewVM) {

    val gameState by viewModel.gameState.collectAsState()
    val destination = remember { mutableStateOf(Route.GAME) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {

        when (destination.value) {
            Route.GAME -> {
                GameView(
                    gameState = gameState,
                    onNewGame = {
                        viewModel.newGame()
                    },
                    onLeaderBoards = {
                        destination.value = Route.LEADERBOARD
                    },
                    onTap = { offset ->
                        viewModel.onTap(offset = offset)
                    },
                    modifier = Modifier.fillMaxSize(),
                    bgColor = viewModel.bgColor
                )
            }

            else -> {
                LeaderboardView(
                    onBack = {
                        destination.value = Route.GAME
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeViewPreview() {
    ChickenTapTheme {
        val game = ChickenTap()
        HomeView(GameViewVM(game = game))
    }
}
