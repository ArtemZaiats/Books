package com.books.app.books.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.books.app.R
import com.books.app.books.presentation.model.BookModel
import com.books.app.utils.nunitoSansFontFamily

@Composable
fun BookItem(
    book: BookModel,
    textColor: Color,
    onBookClick: (BookModel) -> Unit
) {
    Column(
        modifier = Modifier.clickable { onBookClick(book) }
    ) {
        Box(
            modifier = Modifier
                .defaultMinSize(minWidth = 120.dp, minHeight = 150.dp)
        ) {
            AsyncImage(
                model = book.coverUrl,
                contentDescription = "book cover",
                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp)
                    .clip(shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_book),
            )
        }
        Text(
            text = book.name,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontFamily = nunitoSansFontFamily,
                fontWeight = FontWeight(600),
                fontSize = 16.sp,
                color = textColor
//                color = Color(0xFFFFFFFF).copy(alpha = 0.7f),
            ),
            modifier = Modifier
                .width(120.dp)
                .padding(top = 4.dp)
        )
    }
}