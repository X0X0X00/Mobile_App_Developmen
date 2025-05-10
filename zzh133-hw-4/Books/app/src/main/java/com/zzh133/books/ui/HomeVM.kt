package com.zzh133.books.ui

import androidx.lifecycle.ViewModel
import com.zzh133.books.ui.data.Book
import com.zzh133.books.ui.data.BookTestData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update


data class HomeUIState(
    val books: List<Book> = emptyList(),
    val selectedBook: Book? = null,
    val showingDetail: Boolean = false,
)

class HomeVM : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState

    init {
        initList()
    }

    private fun initList() {
        val books = BookTestData.allBooks
        _uiState.value = HomeUIState(
            books = books,
            selectedBook = books.first()
        )
    }
}