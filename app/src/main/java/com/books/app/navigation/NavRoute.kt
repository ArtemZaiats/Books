package com.books.app.navigation

import com.books.app.books.presentation.model.BookModel
import kotlinx.serialization.Serializable

sealed class NavRoute {
    @Serializable data object SplashScreen : NavRoute()
    @Serializable data object MainScreen : NavRoute()
    @Serializable data class DetailsScreen(val bookId: Int) : NavRoute()

}