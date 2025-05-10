package com.zzh133.books.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zzh133.books.ui.data.Book

@Composable
fun BookListScreen(viewModel: HomeVM) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(uiState.books) { book ->
            BookItem(book)
        }
    }
}

@Composable
fun BookItem(book: Book) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(book.purchaseLink))
                context.startActivity(intent)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        // no need change from here
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            Image(
                painterResource(id = book.image),
                contentDescription = null
            )
            Text(
                text = "${book.index}. ${book.title}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Author: ${book.author}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
            )
            Text(
                "Released: ${book.releaseDate}",
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                book.quote,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic,
            )
            Text(
                book.synopsis,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(Modifier.size(32.dp))
        }
    }
}

@Composable
fun BookList(books: List<Book>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = books,
            itemContent = { book ->
                BookItem(book = book)
            }
        )
    }
}


@Composable
fun BooksApp(homeUIState: HomeUIState, viewModel: HomeVM, modifier: Modifier) {
    BookList(books = homeUIState.books)
}

