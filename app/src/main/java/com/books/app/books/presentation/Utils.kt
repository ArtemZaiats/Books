package com.books.app.books.presentation

import com.books.app.books.presentation.model.BookModel

fun groupBooksByGenre(books: List<BookModel>) = books.groupBy { it.genre }

fun filterBooksByLiked(books: List<BookModel>, liked: List<Int>) = books.filter { it.id in liked }