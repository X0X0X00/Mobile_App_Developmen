package com.csc.chickentap.ui.views.gameview

import android.content.res.Configuration
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.csc.chickentap.model.ChickenTap
import com.csc.chickentap.model.GameState
import com.csc.chickentap.model.imgDimens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.sqrt

class GameViewVM(private val game: ChickenTap) : ViewModel() {

    private val _gameState = MutableStateFlow(game.state)
    val gameState: StateFlow<GameState> = _gameState

    private var worldSize: IntSize = IntSize.Zero

    // 新增：用于距离反馈的背景颜色
    private val _bgColor = MutableStateFlow(Color.Blue)
    val bgColor: MutableStateFlow<Color> = _bgColor

    fun setWorldSize(density: Density, config: Configuration) {
        val wPx = with(density) {
            config.screenWidthDp.dp.roundToPx() - imgDimens
        }
        val hPx = with(density) {
            config.screenHeightDp.dp.roundToPx() - imgDimens
        }
        worldSize = IntSize(wPx, hPx)

        _gameState.update {
            it.copy(
                imgLocation = IntOffset(worldSize.width / 2, worldSize.height / 3)
            )
        }
    }

    fun newGame() {
        game.startGame(worldSize = worldSize)
        _gameState.update {
            it.copy(
                gameOver = game.state.gameOver,
                score = game.state.score,
                imgLocation = game.state.imgLocation,
            )
        }
        _bgColor.value = Color.Blue // 重置背景
    }

    fun onTap(offset: IntOffset) {
        if (_gameState.value.gameOver) return

        val correct = game.checkLocation(offset)

        // 距离计算
        val dx = offset.x - (game.state.imgLocation.x + imgDimens / 2)
        val dy = offset.y - (game.state.imgLocation.y + imgDimens / 2)
        val distance = sqrt((dx * dx + dy * dy).toDouble())

        // 背景颜色反馈逻辑
        _bgColor.value = when {
            distance < 300 -> Color.Red
            distance < 500 -> Color.Yellow
            else -> Color.Blue
        }

        _gameState.update {
            it.copy(
                score = game.state.score,
                gameOver = game.state.gameOver
            )
        }
    }
}

class GameViewViewModelFactory(private val model: ChickenTap) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameViewVM(model) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
