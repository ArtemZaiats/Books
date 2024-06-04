package com.books.app.books.presentation.model

import com.books.app.books.domain.model.Library

data class LibraryModel(
    val books: List<BookModel>,
    val banners: List<BannerModel>,
    val likeSection: List<Int>
)

fun Library.asPresentation() = LibraryModel(
    books = books.map { it.asPresentation() },
    banners = banners.map { it.asPresentation() },
    likeSection = likeSection
)
