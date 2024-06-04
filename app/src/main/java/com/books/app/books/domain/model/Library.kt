package com.books.app.books.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Library(
    val books: List<Book>,
    val banners: List<Banner>,
    val likeSection: List<Int>
)