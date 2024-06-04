package com.books.app.books.data.local

import android.content.Context
import androidx.annotation.RawRes
import com.books.app.R
import com.books.app.books.data.dto.LibraryDTO
import com.books.app.books.domain.model.Library
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

class BooksLocalDataSource @Inject constructor(
    private val context: Context
) {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    fun getLibraryData(@RawRes rawResId: Int = R.raw.books_data): LibraryDTO {
        val inputStream = context.resources.openRawResource(rawResId)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val jsonString = bufferedReader.use { it.readText() }

        val adapter = moshi.adapter(LibraryDTO::class.java)
        return adapter.fromJson(jsonString) ?: throw IllegalStateException("Error parsing JSON")
    }
}