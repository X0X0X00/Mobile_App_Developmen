package com.zzh133.books

import android.os.Bundle
import androidx.activity.viewModels
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zzh133.books.data.BookTestData
import com.zzh133.books.ui.theme.BooksTheme
import com.zzh133.books.ui.HomeVM
import com.zzh133.books.ui.HomeUIState
import com.zzh133.books.ui.BooksApp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {

    private val viewModel: HomeVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {

            val uiState by viewModel.uiState.collectAsState()

            BooksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BooksApp(
                        homeUIState = uiState,
                        showDetail = {
                            viewModel.selectBook(it)
                        },
                        hideDetail = {
                            viewModel.closeDetail()
                        },
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun GreetingPreview() {
    val fakeViewModel = HomeVM()
    BooksTheme {
        Surface{
            BooksApp(
                homeUIState = HomeUIState(
                    books = BookTestData.allBooks,
                ),
                showDetail = {},
                hideDetail = {},
                modifier = Modifier.fillMaxSize(),
                viewModel = fakeViewModel

            )
        }
    }
}
