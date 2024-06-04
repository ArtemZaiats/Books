package com.books.app.books.data.dto

import com.books.app.books.domain.model.Banner
import com.books.app.books.domain.model.Book
import com.books.app.books.domain.model.Library
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LibraryDTO(
    @Json(name = "books") val books: List<BookDTO>,
    @Json(name = "top_banner_slides")val banners: List<BannerDTO>,
    @Json(name = "you_will_like_section")val likeSection: List<Int>
)

fun LibraryDTO.asDomain() = Library(
    books = books.map { it.asDomain() },
    banners = banners.map { it.asDomain() },
    likeSection = likeSection
)
