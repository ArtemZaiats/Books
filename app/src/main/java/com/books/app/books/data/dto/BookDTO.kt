package com.books.app.books.data.dto

import com.books.app.books.domain.model.Book
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "author") val author: String,
    @Json(name = "summary") val summary: String,
    @Json(name = "genre") val genre: String,
    @Json(name = "cover_url") val coverUrl: String,
    @Json(name = "views") val views: String,
    @Json(name = "likes") val likes: String,
    @Json(name = "quotes") val quotes: String,
)

fun BookDTO.asDomain() = Book(
    id = id,
    name = name,
    author = author,
    summary = summary,
    genre = genre,
    coverUrl = coverUrl,
    views = views,
    likes = likes,
    quotes = quotes,
)
