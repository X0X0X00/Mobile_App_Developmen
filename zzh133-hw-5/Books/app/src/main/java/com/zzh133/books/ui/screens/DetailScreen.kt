package com.zzh133.books.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zzh133.books.data.Book
import com.zzh133.books.R
import com.zzh133.books.data.BookTestData


@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    book: Book,
    onCloseDetail: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(Modifier.size(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            val imgModifier = Modifier
                .aspectRatio(1f)
            Image(
                painterResource(id = book.image),
                contentDescription = null,
                modifier = imgModifier
            )

        }
        Column {
            Text(
                text = "${book.index}. ${book.title}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                "Author: ${book.author}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "Released: ${book.releaseDate}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                book.synopsis,
                style = MaterialTheme.typography.bodySmall
            )
//            Text(
//                "Link: ${book.purchaseLink}",
//                style = MaterialTheme.typography.bodySmall
//            )

        }

        Spacer(Modifier.size(120.dp))


        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onCloseDetail
            ) {
                Text(stringResource(id = R.string.back))
            }
        }

        Spacer(Modifier.size(50.dp))

    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    Surface {
        DetailScreen(
            book = BookTestData.allBooks.first(),
            onCloseDetail = {}
        )
    }
}

//@Composable
//fun DetailScreen(
//    modifier: Modifier = Modifier,
//    book: Book,
//    onCloseDetail: () -> Unit,
//) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceAround,
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Spacer(Modifier.size(10.dp))
//
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier
//                .padding(vertical = 8.dp)
//        ) {
//            val imgModifier = Modifier
//                .aspectRatio(1f)
//            Image(
//                painterResource(id = book.image),
//                contentDescription = null,
//                modifier = imgModifier
//            )
//
//        }
//        Column {
//            Text(
//                text = "${book.index}. ${book.title}",
//                style = MaterialTheme.typography.titleMedium
//            )
//            Text(
//                "Author: ${book.author}",
//                style = MaterialTheme.typography.bodySmall,
//                fontWeight = FontWeight.Bold,            )
//            Text(
//                text = "Released: ${book.releaseDate}",
//                style = MaterialTheme.typography.bodySmall,
//                maxLines = 1, // 限制最大行数为 1（防止换行）
//                overflow = TextOverflow.Clip // 超出部分直接截断（不换行）
//            )
//            Text(
//                book.synopsis,
//                style = MaterialTheme.typography.bodySmall
//            )
//
//        }
//        Spacer(Modifier.size(120.dp))
//
//
//        Row(
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Button(
//                onClick = onCloseDetail
//            ) {
//                Text(stringResource(id = R.string.back))
//            }
//        }
//        Spacer(Modifier.size(50.dp))
//
//    }
//}

//@Preview
//@Composable
//fun DetailScreenPreview() {
//    Surface {
//        DetailScreen(
//            book = BookTestData.allBooks.first(),
//            onCloseDetail = {}
//        )
//    }
//}
