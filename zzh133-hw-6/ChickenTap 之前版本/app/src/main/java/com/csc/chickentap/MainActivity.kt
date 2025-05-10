package com.csc.chickentap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.ViewModelProvider
import com.csc.chickentap.model.ChickenTap
import com.csc.chickentap.ui.theme.ChickenTapTheme
import com.csc.chickentap.ui.views.HomeView
import com.csc.chickentap.ui.views.gameview.GameViewVM
import com.csc.chickentap.ui.views.gameview.GameViewViewModelFactory


class MainActivity : ComponentActivity() {

    private val chickenTap = ChickenTap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this, GameViewViewModelFactory(model = chickenTap))[GameViewVM::class.java]

        setContent {
            viewModel.setWorldSize(LocalDensity.current, LocalConfiguration.current)
            ChickenTapTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeView(viewModel)
                }
            }
        }
    }
}
