package com.books.app.books.presentation.model

import com.books.app.books.domain.model.Banner

data class BannerModel(
    val id: Int,
    val bookId: Int,
    val cover: String
)

fun Banner.asPresentation() = BannerModel(
    id = id,
    bookId = bookId,
    cover = cover
)

fun List<Banner>.asPresentation(): List<BannerModel> = map { it.asPresentation() }