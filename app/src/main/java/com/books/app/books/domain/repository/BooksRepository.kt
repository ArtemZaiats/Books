package com.books.app.books.domain.repository

import com.books.app.books.domain.model.Library

interface BooksRepository {
    suspend fun getAllLocalBooks(): Library
    suspend fun getAllNetworkBooks(): Library
}