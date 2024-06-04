package com.books.app.books.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.books.app.books.presentation.model.BookModel

@Composable
fun BooksList(
    modifier: Modifier = Modifier,
    books: List<BookModel>,
    onBookClick: (BookModel) -> Unit,
    textColor: Color
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(books) { book ->
            BookItem(
                book = book,
                textColor = textColor,
                onBookClick = onBookClick
            )
        }
    }
}