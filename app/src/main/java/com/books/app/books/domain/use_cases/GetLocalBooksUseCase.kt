package com.books.app.books.domain.use_cases

import com.books.app.books.domain.model.Library
import com.books.app.books.domain.repository.BooksRepository
import javax.inject.Inject

class GetLocalBooksUseCase @Inject constructor(
    private val booksRepository: BooksRepository
) {

    suspend operator fun invoke(): Library = booksRepository.getAllLocalBooks()

}