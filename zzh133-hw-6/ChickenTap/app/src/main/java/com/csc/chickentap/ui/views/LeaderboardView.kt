package com.csc.chickentap.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.csc.chickentap.R


@Composable
fun LeaderboardView(
    onBack: () -> Unit,
    modifier: Modifier
) {
    Surface(
        color = Color.DarkGray,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier.padding(32.dp),
            ) {
                Text(
                    stringResource(R.string.back)
                )
            }
        }
    }
}

@Preview
@Composable
fun LeaderBoardPreview() {
    LeaderboardView(
        onBack = {},
        modifier = Modifier.fillMaxSize()
    )
}

