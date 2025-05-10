package com.zzh133.Memez

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zzh133.Memez.ui.theme.MemezTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemezTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomePage(
                        viewModel = Memez(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomePage(viewModel: Memez, modifier: Modifier = Modifier) {
    var bgcolor by remember { mutableStateOf(Color.White) }
    val context = LocalContext.current  // 将 context 提前获取，避免作用域问题

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(bgcolor),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Spacer(Modifier.height(10.dp))

        Text(
            text = stringResource(R.string._01, viewModel.totalMemes) + " ${viewModel.totalMemes} "+
                    stringResource(R.string._02, if (viewModel.totalMemes == 1) "" else "s"),
            fontSize = 25.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )


        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {

            Image(
                painter = painterResource(id = viewModel.imageList[viewModel.memeImageIndex]),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.Center)
            )

            Text(
                text = stringResource(id = viewModel.stringList[viewModel.memeTextIndex]), // 替换为你的文本
                color = Color.White, // 文字颜色
                fontSize = 16.sp, // 字体大小
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.BottomCenter) // 文字对齐到底部中央
                    .padding(8.dp) // 文字与边缘的间距
                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp)) // 半透明背景
                    .padding(4.dp) // 文字与背景之间的间距
            )
        }

        Spacer(Modifier.height(100.dp))


        Row {
            Button(onClick = {
                viewModel.clearMeme()
                Toast.makeText(
                    context,
                    context.getString(R.string._06),
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Text(stringResource(id = R.string._03))
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(onClick = {
                viewModel.nextMeme()
            }) {
                Text(stringResource(id = R.string._04))
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(onClick = {
                viewModel.downLoadMeme(context)
                Toast.makeText(
                    context,
                    context.getString(R.string._07),
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Text(stringResource(id = R.string._05))
            }
        }
        Spacer(Modifier.height(10.dp))
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewHomePage() {
    MemezTheme {
        HomePage(Memez())
    }
}

