package com.books.app.books.data.di

import android.content.Context
import com.books.app.books.data.BooksDataRepository
import com.books.app.books.domain.repository.BooksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BooksDataModule {

    @Binds
    abstract fun bindBooksRepository(impl: BooksDataRepository): BooksRepository
}