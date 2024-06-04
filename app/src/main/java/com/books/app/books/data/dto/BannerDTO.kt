package com.books.app.books.data.dto

import com.books.app.books.domain.model.Banner
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BannerDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "book_id") val bookId: Int,
    @Json(name = "cover") val cover: String
)

fun BannerDTO.asDomain() = Banner(
    id = id,
    bookId = bookId,
    cover = cover
)
