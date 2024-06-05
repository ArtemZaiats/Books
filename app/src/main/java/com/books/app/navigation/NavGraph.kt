package com.books.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.books.app.books.presentation.BooksViewModel
import com.books.app.books.presentation.DetailsScreen
import com.books.app.books.presentation.MainScreen
import com.books.app.books.presentation.SplashScreen

@Composable
fun NavGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    val booksViewModel: BooksViewModel = hiltViewModel()
    val booksState = booksViewModel.library.collectAsState()

    NavHost(
        navController = navController,
        startDestination = NavRoute.SplashScreen
    ) {
        composable<NavRoute.SplashScreen> {
            SplashScreen(
                modifier = modifier,
                onTimeout = {
                    navController.navigate(NavRoute.MainScreen) {
                        popUpTo(NavRoute.SplashScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<NavRoute.MainScreen> {
            MainScreen(
                modifier = modifier,
                booksState = booksState.value,
                onBookClick = {
                    navController.navigate(NavRoute.DetailsScreen(it.id))
                },
                onBannerClick = {
                    navController.navigate(NavRoute.DetailsScreen(it.bookId))
                }
            )
        }
        composable<NavRoute.DetailsScreen> {
            val args = it.toRoute<NavRoute.DetailsScreen>()
            val bookId = args.bookId
            DetailsScreen(
                modifier = modifier,
                booksState = booksState.value,
                bookId = bookId,
                onBookClick = {
                    navController.navigate(NavRoute.DetailsScreen(bookId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

