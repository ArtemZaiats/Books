package com.books.app.books.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.books.app.R
import com.books.app.books.presentation.components.BooksList
import com.books.app.books.presentation.model.BookModel
import kotlin.math.absoluteValue

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    booksState: BooksUIState,
    bookId: Int,
    onBookClick: (BookModel) -> Unit,
    onBackClick: () -> Unit,
) {
    val books = booksState.library.books
    val recommendedBooks = filterBooksByLiked(books, booksState.library.likeSection)
    val pagerState = rememberPagerState(
        pageCount = { books.size },
        initialPage = bookId,
    )
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_details_screen),
            contentDescription = "details screen background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            DetailsTopBar(onBackClick)

            DetailsHeader(
                modifier = Modifier
                    .fillMaxSize(),
                books = books,
                recommendedBooks = recommendedBooks,
                onBookClick = onBookClick,
                pagerState = pagerState
            )
        }
    }
}

@Composable
private fun DetailsTopBar(onBackClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp)
            .size(40.dp)
            .clip(shape = CircleShape)
            .clickable { onBackClick() }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = stringResource(R.string.back_icon),
            modifier = Modifier.size(24.dp),
            alignment = Alignment.Center,
            colorFilter = ColorFilter.tint(color = Color.White)
        )
    }
}

@Composable
fun DetailsHeader(
    modifier: Modifier = Modifier,
    books: List<BookModel>,
    recommendedBooks: List<BookModel>,
    onBookClick: (BookModel) -> Unit,
    pagerState: PagerState
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(state = rememberScrollState())
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 96.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        ) { index ->
            val pageOffset = (
                    (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
                    ).absoluteValue

            val scale = lerp(
                start = 0.7f,
                stop = 1f,
                fraction = 1f - pageOffset.coerceIn(0f, 1f)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = lerp(
                            start = 0.4f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleX = scale
                        scaleY = scale
                    },
            ) {
                AsyncImage(
                    model = books[index].coverUrl,
                    contentDescription = stringResource(R.string.banner_description),
                    modifier = Modifier
                        .width(200.dp)
                        .height(250.dp)
                        .clip(shape = RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = books[index].name,
                    style = TextStyle(
                        color = Color(0xffFFFFFF),
                        fontSize = 20.sp,
                        fontWeight = FontWeight(700),
                        lineHeight = 22.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = books[index].author,
                    style = TextStyle(
                        color = Color(0xffFFFFFF),
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700),
                        lineHeight = 16.sp
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        BookDetails(
            book = books[pagerState.currentPage],
            recommendedBooks = recommendedBooks,
            onBookClick = onBookClick
        )
    }
}

@Composable
fun BookDetails(
    modifier: Modifier = Modifier,
    book: BookModel,
    recommendedBooks: List<BookModel>,
    onBookClick: (BookModel) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(vertical = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = book.views,
                    style = TextStyle(
                        color = Color(0xff0B080F),
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700),
                        lineHeight = 22.sp
                    )
                )
                Text(
                    text = stringResource(R.string.readers),
                    style = TextStyle(
                        color = Color(0xffD9D5D6),
                        fontSize = 12.sp,
                        fontWeight = FontWeight(600),
                        lineHeight = 13.2.sp
                    )
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = book.likes,
                    style = TextStyle(
                        color = Color(0xff0B080F),
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700),
                        lineHeight = 22.sp
                    )
                )
                Text(
                    text = stringResource(R.string.likes),
                    style = TextStyle(
                        color = Color(0xffD9D5D6),
                        fontSize = 12.sp,
                        fontWeight = FontWeight(600),
                        lineHeight = 13.2.sp
                    )
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = book.quotes,
                    style = TextStyle(
                        color = Color(0xff0B080F),
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700),
                        lineHeight = 22.sp
                    )
                )
                Text(
                    text = stringResource(R.string.quotes),
                    style = TextStyle(
                        color = Color(0xffD9D5D6),
                        fontSize = 12.sp,
                        fontWeight = FontWeight(600),
                        lineHeight = 13.2.sp
                    )
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = book.genre,
                    style = TextStyle(
                        color = Color(0xff0B080F),
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700),
                        lineHeight = 22.sp
                    )
                )
                Text(
                    text = stringResource(R.string.genre),
                    style = TextStyle(
                        color = Color(0xffD9D5D6),
                        fontSize = 12.sp,
                        fontWeight = FontWeight(600),
                        lineHeight = 13.2.sp
                    )
                )
            }
        }
        HorizontalDivider(
            color = Color(0xffD9D5D6),
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Summary(book = book)
        HorizontalDivider(
            color = Color(0xffD9D5D6),
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        AlsoLike(
            recommendedBooks = recommendedBooks,
            onBookClick = onBookClick
        )
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffDD48A1)
            )
        ) {
            Text(
                text = stringResource(R.string.read_now),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(800),
                )
            )
        }
    }
}

@Composable
fun Summary(modifier: Modifier = Modifier, book: BookModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.summary),
            style = TextStyle(
                color = Color(0xff0B080F),
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                lineHeight = 22.sp
            )
        )
        Text(
            text = book.summary,
            style = TextStyle(
                color = Color(0xFF393637),
                fontSize = 14.sp,
                fontWeight = FontWeight(600),
                lineHeight = 16.8.sp
            )
        )
    }
}

@Composable
fun AlsoLike(
    modifier: Modifier = Modifier,
    recommendedBooks: List<BookModel>,
    onBookClick: (BookModel) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.you_will_also_like),
            style = TextStyle(
                color = Color(0xff0B080F),
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                lineHeight = 22.sp
            )
        )
        BooksList(
            books = recommendedBooks,
            onBookClick = {},
            textColor = Color(0xff393637)
        )
    }
}
