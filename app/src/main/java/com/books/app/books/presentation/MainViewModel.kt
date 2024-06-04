package com.books.app.books.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.books.app.books.domain.use_cases.GetLocalBooksUseCase
import com.books.app.books.domain.use_cases.GetRemoteBooksUseCase
import com.books.app.books.presentation.model.LibraryModel
import com.books.app.books.presentation.model.asPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(private val getRemoteBooksUseCase: GetRemoteBooksUseCase) :
    ViewModel() {

    val library: StateFlow<BooksUIState> = flow {
        emit(getRemoteBooksUseCase())
    }.map {
        BooksUIState(library = it.asPresentation())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BooksUIState()
    )
}

data class BooksUIState(
    val library: LibraryModel = LibraryModel(
        books = listOf(),
        banners = listOf(),
        likeSection = listOf()
    )
)