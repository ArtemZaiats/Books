package com.books.app.books.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.books.app.R
import com.books.app.books.presentation.components.BooksList
import com.books.app.books.presentation.model.BannerModel
import com.books.app.books.presentation.model.BookModel
import com.books.app.utils.nunitoSansFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    booksState: BooksUIState,
    onBookClick: (BookModel) -> Unit,
    onBannerClick: (BannerModel) -> Unit
) {
    val booksByGenre = groupBooksByGenre(booksState.library.books)
    val banners = booksState.library.banners
    val pagerState = rememberPagerState(
        pageCount = { Int.MAX_VALUE },
        initialPage = Int.MAX_VALUE / 2
    )
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.library),
                        style = TextStyle(
                            fontFamily = nunitoSansFontFamily,
                            fontWeight = FontWeight(700),
                            fontSize = 20.sp,
                            color = Color(0xFFD0006E),
                        )
                    )
                },
                windowInsets = TopAppBarDefaults.windowInsets,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff101010)
                )
            )
        }
    ) { paddingValues ->
        LaunchedEffect(pagerState) {
            while (true) {
                delay(3000)
                coroutineScope.launch {
                    val nextPage =
                        if (pagerState.currentPage == banners.size - 1) 0 else pagerState.currentPage + 1
                    pagerState.animateScrollToPage(nextPage)
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(state = rememberScrollState())
                .background(color = Color(0xff101010))
                .padding(16.dp)
        ) {
            BannerSlider(
                banners = banners,
                pagerState = pagerState,
                onBannerClick = onBannerClick
            )
            Books(
                booksByGenre = booksByGenre,
                onBookClick = onBookClick
            )
        }
    }
}

@Composable
fun BannerSlider(
    banners: List<BannerModel>,
    pagerState: PagerState,
    onBannerClick: (BannerModel) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,

            ) { index ->
            val actualIndex = index % banners.size
            AsyncImage(
                model = banners[actualIndex].cover,
                contentDescription = stringResource(R.string.banner_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .size(160.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .clickable { onBannerClick(banners[actualIndex]) },
                contentScale = ContentScale.Crop
            )
        }

        Indicator(
            modifier = Modifier.align(Alignment.BottomCenter),
            pagerState = pagerState,
            itemCount = banners.size
        )
    }
}

@Composable
fun Indicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    itemCount: Int
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        for (i in 0 until itemCount) {
            val color =
                if (i == pagerState.currentPage % itemCount) Color(0xffD0006E) else Color(0xffC1C2CA)
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(10.dp)
                    .clip(RoundedCornerShape(50))
                    .background(color = color, shape = RoundedCornerShape(50.dp))
            )
        }
    }
}

@Composable
fun Books(
    booksByGenre: Map<String, List<BookModel>>,
    onBookClick: (BookModel) -> Unit
) {
    booksByGenre.forEach { (genre, books) ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = genre,
                style = TextStyle(
                    fontFamily = nunitoSansFontFamily,
                    fontWeight = FontWeight(700),
                    fontSize = 20.sp,
                    color = Color(0xFFFFFFFF),
                ),
            )
            BooksList(
                books = books,
                textColor = Color(0xFFFFFFFF).copy(alpha = 0.7f),
                onBookClick = onBookClick
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

