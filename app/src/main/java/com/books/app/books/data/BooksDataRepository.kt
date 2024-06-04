package com.books.app.books.data

import com.books.app.books.data.local.BooksLocalDataSource
import com.books.app.books.data.dto.asDomain
import com.books.app.books.data.network.BooksRemoteDataSource
import com.books.app.books.domain.model.Library
import com.books.app.books.domain.repository.BooksRepository
import javax.inject.Inject

class BooksDataRepository @Inject constructor(
    private val booksLocalDataSource: BooksLocalDataSource,
    private val booksRemoteDataSource: BooksRemoteDataSource
) : BooksRepository {

    override suspend fun getAllLocalBooks(): Library {
        return booksLocalDataSource.getLibraryData().asDomain()
    }

    override suspend fun getAllNetworkBooks(): Library {
        return booksRemoteDataSource.fetchLibraryData().asDomain()
    }

}