package com.books.app.books.data.di

import android.content.Context
import com.books.app.books.data.local.BooksLocalDataSource
import com.books.app.books.data.network.BooksRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BooksDataSourceModule {
    @Provides
    @Singleton
    fun provideBooksLocalDataSource(@ApplicationContext context: Context): BooksLocalDataSource =
        BooksLocalDataSource(context)

    @Provides
    @Singleton
    fun provideBooksRemoteDataSource(@ApplicationContext context: Context): BooksRemoteDataSource =
        BooksRemoteDataSource(context)
}