package com.zzh133.books.ui.screens
import com.zzh133.books.ui.screens.DetailScreen
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zzh133.books.ui.HomeUIState
import com.zzh133.books.data.Book
import com.zzh133.books.data.BookTestData
import com.zzh133.books.ui.HomeVM
import androidx.compose.material3.Button
import androidx.compose.ui.res.stringResource
import com.zzh133.books.R


@Composable
fun HomeScreen(
    homeUIState: HomeUIState,
    showDetail: (Int) -> Unit,
    hideDetail: () -> Unit,
    selectedBook: Book? = null,
    viewModel: HomeVM,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {

        if (homeUIState.selectedBook != null && homeUIState.showingDetail) {
            BackHandler {
                hideDetail()
            }
            DetailScreen(
                book = homeUIState.selectedBook
            ) {
                hideDetail()
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(
                    items = homeUIState.books,
                    key = { it.index },
                    itemContent = { book ->
                        BookItem(
                            book = book,
                            isSelected = book.index == selectedBook?.index,
                        ) { index ->
                            showDetail(index)
                        }
                    }
                )
                item {
                    Spacer(Modifier.width(20.dp))
                    Button(
                        onClick = {
                            val randomBook = viewModel.getRandomBook()
                            showDetail(randomBook.index)
                        },
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Text(stringResource(id = R.string.Suggestion))
                    }
                }
            }
        }
    }
}

@Composable
fun BookItem(
    book: Book,
    isSelected: Boolean = false,
    showDetail: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .semantics { selected = isSelected }
            .clickable { showDetail(book.index) },
    ) {
        val imgModifier = Modifier
            .size(64.dp)
        Row {
            Image(
                painterResource(id = book.image),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = imgModifier
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = "${book.index}. ${book.title}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    "Author: ${book.author}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    "Released: ${book.releaseDate}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Spacer(Modifier.size(15.dp))
        Text(
            book.quote,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
        )
        Spacer(Modifier.size(5.dp))
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    Surface {
        HomeScreen(
            homeUIState = HomeUIState(
                books = BookTestData.allBooks,
            ),
            showDetail = {},
            hideDetail = {},
            viewModel = HomeVM()  // 添加这行
        )
    }
}