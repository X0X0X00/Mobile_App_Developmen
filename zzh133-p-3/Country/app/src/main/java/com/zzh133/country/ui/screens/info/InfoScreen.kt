package com.zzh133.country.ui.screens.info

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zzh133.country.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun InfoScreen(navController: NavController) {
    val context = LocalContext.current
    val packageManager = context.packageManager
    val packageName = context.packageName

    val applicationInfo = packageManager.getApplicationInfo(packageName, 0)
    val appName = packageManager.getApplicationLabel(applicationInfo).toString()

    val packageInfo = packageManager.getPackageInfo(packageName, 0)
    val versionName = packageInfo.versionName ?: "N/A"
    val versionCode = packageInfo.longVersionCode

    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val formattedDate = currentDate.format(formatter)

    // 动画控制：图标缩放&透明
    var startAnimation by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "icon_scale_anim"
    )
    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "icon_alpha_anim"
    )

    // 动画控制：文字呼吸动画
    val infiniteTransition = rememberInfiniteTransition(label = "font_breath_anim")
    val fontScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "font_scale_anim"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 带动画的 App Icon ✨
        Image(
            painter = painterResource(id = R.drawable.image),
            contentDescription = stringResource(id = R.string.app_icon_desc),
            modifier = Modifier
                .size(64.dp)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    alpha = alpha
                )
                .clip(CircleShape)
        )

        Spacer(Modifier.height(24.dp))

        // 动画版 Info 文本
        AnimatedInfoText(text = stringResource(id = R.string.app_name_info, appName), scale = fontScale)
        AnimatedInfoText(text = stringResource(id = R.string.version_info, versionName), scale = fontScale)
        AnimatedInfoText(text = stringResource(id = R.string.build_number_info, versionCode), scale = fontScale)
        AnimatedInfoText(text = stringResource(id = R.string.build_date_info, formattedDate), scale = fontScale)
        AnimatedInfoText(text = stringResource(id = R.string.developer_id_info, "zzh133"), scale = fontScale)
    }
}

// ✨ 通用的动画文字组件
@Composable
fun AnimatedInfoText(text: String, scale: Float) {
    Text(
        text = text,
        modifier = Modifier.graphicsLayer(
            scaleX = scale,
            scaleY = scale
        )
    )
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewInfoScreen() {
    InfoScreen(navController = rememberNavController())
}
