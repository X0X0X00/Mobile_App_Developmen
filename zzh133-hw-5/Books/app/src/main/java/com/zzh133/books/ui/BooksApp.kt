package com.zzh133.books.ui


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zzh133.books.ui.screens.HomeScreen

@Composable
fun BooksApp(
    homeUIState: HomeUIState,
    showDetail: (Int) -> Unit = {},
    hideDetail: () -> Unit = {},
    modifier: Modifier,
    viewModel: HomeVM,
) {
    Column {
        HomeScreen(
            homeUIState = homeUIState,
            showDetail = showDetail,
            hideDetail = hideDetail,
            selectedBook = homeUIState.selectedBook,
            modifier = Modifier.weight(1f),
            viewModel = viewModel, // 传入 ViewModel 用于“随机推荐一本书”等功能
        )
    }
}


