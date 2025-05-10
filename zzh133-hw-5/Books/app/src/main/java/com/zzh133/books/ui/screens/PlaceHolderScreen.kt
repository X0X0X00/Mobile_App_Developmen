package com.zzh133.books.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zzh133.books.R
import com.zzh133.books.ui.theme.BooksTheme

@Composable
fun PlaceholderScreen(
    modifier: Modifier = Modifier,
    title: String = "",
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize(),
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceholderScreenPreview() {
    BooksTheme{
        Surface(tonalElevation = 5.dp) {
            PlaceholderScreen(
                title = stringResource(id = R.string.placeholder)
            )
        }
    }
}