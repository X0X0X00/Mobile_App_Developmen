package com.zzh133.mood


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zzh133.mood.ui.theme.MoodTheme



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoodTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomePage(
                        viewModel = Mood(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}



@Composable
fun HomePage(viewModel: Mood, modifier: Modifier = Modifier) {
    var bgcolor by remember { mutableStateOf(Color.White) }

    Column(
        modifier = Modifier.fillMaxSize().background(bgcolor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(80.dp))

        Text(
            text = "Mood",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(Modifier.height(20.dp))


        Image(
            painter = painterResource(id = viewModel.moodImageID),
            contentDescription = null,
            Modifier
                .height(160.dp)
        )

        Spacer(Modifier.height(20.dp))

        Text(
            text = stringResource(id = viewModel.moodTextID),
            fontSize = 18.sp,
            style = MaterialTheme.typography.headlineLarge
        )


        Spacer(Modifier.height(350.dp))

        Row {
            IconButton(onClick = {
                viewModel.clickHappy()
                bgcolor = Color.Yellow
            }) {
                Image(
                    painter = painterResource(id = R.drawable.happy),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(48.dp)
                )
            }


            Spacer(modifier = Modifier.padding(24.dp))


            IconButton(onClick = {
                viewModel.clickSoso()
                bgcolor = Color.Green
            }) {
                Image(
                    painter = painterResource(id = R.drawable.soso),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(48.dp)
                )
            }


            Spacer(modifier = Modifier.padding(24.dp))


            IconButton(onClick = {
                viewModel.clickSad()
                bgcolor = Color.Blue
            }) {
                Image(
                    painter = painterResource(id = R.drawable.sad),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomePage() {
    MoodTheme {
        HomePage(Mood())
    }
}