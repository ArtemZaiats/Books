package com.books.app.books.presentation.model

import android.os.Parcelable
import com.books.app.books.domain.model.Book
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookModel(
    val id: Int,
    val name: String,
    val author: String,
    val summary: String,
    val genre: String,
    val coverUrl: String,
    val views: String,
    val likes: String,
    val quotes: String,
): Parcelable

fun Book.asPresentation() = BookModel(
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

fun List<Book>.asPresentation(): List<BookModel> = map { it.asPresentation() }
