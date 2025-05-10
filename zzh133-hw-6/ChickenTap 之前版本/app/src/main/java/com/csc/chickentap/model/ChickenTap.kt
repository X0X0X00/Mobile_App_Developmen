package com.csc.chickentap.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.random.Random.Default.nextInt


const val imgDimens: Int = 400
const val scoreIncrement: Int = 10

data class GameState(
    var gameOver: Boolean = true,
    var score: Int = 0,
    var imgLocation: IntOffset = IntOffset.Zero,
    var backGroundColor: Color = Color.Blue,
)

class ChickenTap {

    val state = GameState()

    fun startGame(worldSize: IntSize) {
        state.gameOver = false
        state.score = 0
        state.imgLocation = IntOffset(
            nextInt(from = 0, until = worldSize.width),
            nextInt(from = 0, until = worldSize.height)
        )
    }

    // function
//    fun checkLocation(loc: IntOffset) {
//        state.score += scoreIncrement
//        val xRange = state.imgLocation.x..state.imgLocation.x + imgDimens
//        val yRange = state.imgLocation.y..state.imgLocation.y + imgDimens
//        state.gameOver = xRange.contains(loc.x) && yRange.contains(loc.y)
//    }

    // 改成猜对才加分，猜错扣分

    fun checkLocation(loc: IntOffset): Boolean {
        val xRange = state.imgLocation.x..(state.imgLocation.x + imgDimens)
        val yRange = state.imgLocation.y..(state.imgLocation.y + imgDimens)
        val tappedCorrectly = xRange.contains(loc.x) && yRange.contains(loc.y)

        if (tappedCorrectly) {
            state.score += scoreIncrement
            state.gameOver = true  // 游戏结束
        } else {
            state.score -= scoreIncrement / 2  // 猜错扣分，可调节
            state.gameOver = false
        }

        return tappedCorrectly
    }

}
