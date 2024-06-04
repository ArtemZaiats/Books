package com.books.app.books.domain.model

data class Book(
        val id: Int,
        val name: String,
        val author: String,
        val summary: String,
        val genre: String,
        val coverUrl: String,
        val views: String,
        val likes: String,
        val quotes: String,
)