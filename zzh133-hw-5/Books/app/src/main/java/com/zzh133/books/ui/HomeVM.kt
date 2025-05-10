package com.zzh133.books.ui

import androidx.lifecycle.ViewModel
import com.zzh133.books.data.Book
import com.zzh133.books.data.BookTestData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


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

    fun selectBook(idx: Int) {
        val books = uiState.value.books.find { it.index == idx }
        _uiState.value = _uiState.value.copy(
            selectedBook = books,
            showingDetail = true
        )
    }

    fun closeDetail() {
        _uiState.value = _uiState.value.copy(
            selectedBook = _uiState.value.books.first(),
            showingDetail = false
        )
    }

    fun getRandomBook(): Book {
        return _uiState.value.books.random()
    }

}