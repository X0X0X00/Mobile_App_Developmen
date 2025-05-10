package com.csc.chickentap.ui.views.gameview

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.sp
import com.csc.chickentap.R
import com.csc.chickentap.model.GameState
import com.csc.chickentap.model.imgDimens
import com.csc.chickentap.ui.theme.ChickenTapTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun HUD(gameState: GameState) {

    val baseFontSize = 48.sp
    val largeFontSize = 64.sp

    // 每当分数变化，触发动画字体放大
    var prevScore by remember { mutableStateOf(gameState.score) }
    var triggerAnim by remember { mutableStateOf(false) }

    LaunchedEffect(gameState.score) {
        if (gameState.score != prevScore) {
            triggerAnim = true
            prevScore = gameState.score
            delay(150)
            triggerAnim = false
        }
    }

    val animatedFontSize by animateFloatAsState(
        targetValue = if (triggerAnim) largeFontSize.value else baseFontSize.value
    )

    val hudStyle = TextStyle(
        color = Color.White,
        fontSize = baseFontSize,
        fontWeight = FontWeight.SemiBold,
    )

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                stringResource(R.string.score),
                style = hudStyle,
            )
            Spacer(Modifier.size(24.dp))
            Text(
                text = gameState.score.toString(),
                style = hudStyle.copy(fontSize = animatedFontSize.sp),
            )
        }
    }
}


//@Composable
//fun HUD(gameState: GameState) {
//
//    val hudStyle = TextStyle(
//        color = Color.White,
//        fontSize = 48.sp,
//        fontWeight = FontWeight.SemiBold,
//    )
//    Column {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.padding(32.dp)
//        ) {
//            Text(
//                stringResource(R.string.score),
//                style = hudStyle,
//            )
//            Spacer(Modifier.size(24.dp))
//            Text(
//                gameState.score.toString(),
//                style = hudStyle,
//            )
//        }
//    }
//}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun LayersView(
    gameState: GameState,
    onNewGame: () -> Unit,
    onLeaderBoards: () -> Unit,
    modifier: Modifier,
    bgColor: MutableStateFlow<Color> = MutableStateFlow(Color.Blue)
) {
    val image = ImageBitmap.imageResource(id = R.drawable.chicken)

    // 🐔 控制小鸡缩放动画
    var scaleTarget by remember { mutableStateOf(0f) }

    // 当开始新游戏时（gameOver = false），触发缩放动画
    LaunchedEffect(gameState.gameOver) {
        if (!gameState.gameOver) {
            scaleTarget = 0f
            delay(50) // 保证重组
            scaleTarget = 1f
        }
    }

    val scaleFactor by animateFloatAsState(
        targetValue = scaleTarget,
        animationSpec = tween(durationMillis = 400)
    )


    Surface(
        color = bgColor.value,
        modifier = modifier
    ) {
        // HUD
        Box(contentAlignment = Alignment.TopStart) {
            HUD(gameState)
        }

        // 🐔 小鸡图像缩放动画
        Box {
            val alphaAnim = remember { Animatable(0f) }
            val scaleAnim = remember { Animatable(0.5f) }

            LaunchedEffect(gameState.gameOver){
                if(gameState.gameOver){
                    alphaAnim.animateTo(1f, animationSpec = tween(durationMillis = 1000))
                    scaleAnim.animateTo(1f, animationSpec = tween(durationMillis = 1000))
                }
                else{
                    alphaAnim.animateTo(0f)
                    scaleAnim.animateTo(0.5f)
                }
            }

            Canvas(
                modifier = Modifier.fillMaxSize(),
                onDraw = {
                    if (gameState.gameOver) {
                        with(drawContext.canvas.nativeCanvas) {
                            save()
                            translate(
                                gameState.imgLocation.x.toFloat(),
                                gameState.imgLocation.y.toFloat()
                            )
                            scale(scaleAnim.value, scaleAnim.value, center.x, center.y)
                            drawImage(
                                image = image,
                                dstSize = IntSize(width = imgDimens, height = imgDimens),
                                alpha = alphaAnim.value
                                )
                            restore()
                        }
                    }
                }
            )
        }

        // 按钮淡入
        // 控制按钮淡入淡出
        val targetAlpha = if (gameState.gameOver) 1f else 0f
        val animatedAlpha by animateFloatAsState(
            targetValue = targetAlpha,
            animationSpec = tween(durationMillis = 500)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .padding(16.dp)
                .alpha(animatedAlpha)
        ) {
            Button(
                onClick = onLeaderBoards,
                modifier = Modifier.padding(32.dp),
            ) {
                Text(pluralStringResource(id = R.plurals.leaderboard, 1))
            }
            Button(
                onClick = onNewGame,
                modifier = Modifier.padding(32.dp),
            ) {
                Text(stringResource(R.string.new_game))
            }
        }
    }
}


//@Composable
//fun LayersView(
//    gameState: GameState,
//    onNewGame: () -> Unit,
//    onLeaderBoards: () -> Unit,
//    modifier: Modifier,
//) {
//    val image = ImageBitmap.imageResource(id = R.drawable.chicken)
//    Surface(
//        color = gameState.backGroundColor,
//        modifier = modifier
//    ) {
//
//        // HUD
//        Box(
//            contentAlignment = Alignment.TopStart
//        ) {
//            HUD(gameState)
//        }
//
//        // Chicken
//        val scaleFactor by animateFloatAsState(
//            targetValue = if (gameState.gameOver) 1f else 0f
//        )
//
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.TopStart
//        ) {
//            androidx.compose.foundation.Image(
//                bitmap = image,
//                contentDescription = "Chicken",
//                modifier = Modifier
//                    .padding(top = gameState.imgLocation.y.dp, start = gameState.imgLocation.x.dp)
//                    .size(imgDimens.dp)
//                    .scale(scaleFactor)
//            )
//        }

//        Box {
//            Canvas(
//                modifier = Modifier.fillMaxSize(),
//                onDraw = {
//                    if (gameState.gameOver) {
//                        drawImage(
//                            image = image,
//                            dstSize = IntSize(width = imgDimens, height = imgDimens),
//                            dstOffset = IntOffset(
//                                x = gameState.imgLocation.x,
//                                y = gameState.imgLocation.y
//                            )
//                        )
//                    }
//                })
//        }

        // Buttons
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            verticalAlignment = Alignment.Bottom
//        ) {
//            Button(
//                onClick = onLeaderBoards,
//                modifier = Modifier.padding(32.dp),
//            ) {
//                Text(
//                    pluralStringResource(id = R.plurals.leaderboard, 1)
//                )
//            }
//            Button(
//                onClick = onNewGame,
//                modifier = Modifier.padding(32.dp),
//            ) {
//                Text(
//                    stringResource(R.string.new_game)
//                )
//            }
//        }
//        val targetAlpha = if (gameState.gameOver) 1f else 0f
//        val animatedAlpha by animateFloatAsState(targetValue = targetAlpha)
//
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            verticalAlignment = Alignment.Bottom,
//            modifier = Modifier
//                .padding(16.dp)
//                .alpha(animatedAlpha)
//        ) {
//            Button(
//                onClick = onLeaderBoards,
//                modifier = Modifier.padding(32.dp),
//            ) {
//                Text(pluralStringResource(id = R.plurals.leaderboard, 1))
//            }
//            Button(
//                onClick = onNewGame,
//                modifier = Modifier.padding(32.dp),
//            ) {
//                Text(stringResource(R.string.new_game))
//            }
//        }
//
//    }
//}

@Composable
fun GameView(
    gameState: GameState,
    onNewGame: () -> Unit,
    onLeaderBoards: () -> Unit,
    onTap: (offset: IntOffset) -> Unit,
    modifier: Modifier,
    bgColor: MutableStateFlow<Color> = MutableStateFlow(Color.Blue)
)
    {
    Box(
        Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onTap(it.round()) }
                )
            }
    ) {
        LayersView(
            gameState = gameState,
            onNewGame = onNewGame,
            onLeaderBoards = onLeaderBoards,
            modifier = modifier,
            bgColor = bgColor
        )
    }
}

//@Preview
//@Composable
//fun GamePreview() {
//    ChickenTapTheme {
//        GameView(
//            gameState = GameState(gameOver = false), // 初始为 false，按钮应该隐藏
//            onNewGame = {},
//            onLeaderBoards = {},
//            onTap = {},
//            modifier = Modifier.fillMaxSize(),
//        )
//    }
//}

@Preview
@Composable
fun GamePreview() {
    ChickenTapTheme {
        GameView(
            gameState = GameState(gameOver = false), // 初始为 false，按钮应该隐藏
            onNewGame = {},
            onLeaderBoards = {},
            onTap = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}
