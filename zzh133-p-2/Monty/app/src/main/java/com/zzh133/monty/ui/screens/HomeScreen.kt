package com.zzh133.monty.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.zzh133.monty.ui.theme.MontyTheme
import com.zzh133.monty.R


@Composable
fun HomeScreen(navController: NavController) {

    var startAnimation by remember { mutableStateOf(false) }
    val offsetX by animateDpAsState(targetValue = if (startAnimation) 0.dp else 300.dp)

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 应用图标
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 应用名称
        Text(
            text = "Monty Game",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(15.dp))

        // 欢迎信息
        Text(
            text = "Welcome to Monty Game!",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(15.dp))

        // 导航按钮
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .offset(x = offsetX),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { navController.navigate("game") }) {
                Text("Start")
            }

            Button(onClick = { navController.navigate("settings") }) {
                Text("Settings")
            }

            Button(onClick = { navController.navigate("bank") }) {
                Text("Bank")
            }
        }
    }
}

// 添加预览
@Preview(
    showBackground = true,
    widthDp = 640,
    heightDp = 360,
    name = "Landscape Preview"
)
@Composable
fun HomeScreenPreview() {
    MontyTheme {
        HomeScreen(navController = NavController(context = LocalContext.current))
    }
}