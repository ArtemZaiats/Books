package com.books.app.books.data.network

import android.content.Context
import com.books.app.books.data.dto.LibraryDTO
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BooksRemoteDataSource @Inject constructor(
    private val context: Context
) {
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val jsonAdapter = moshi.adapter(LibraryDTO::class.java)

    suspend fun fetchLibraryData(): LibraryDTO {
        remoteConfig.fetchAndActivate().await()
        val json = remoteConfig["json_data"].asString()
        return jsonAdapter.fromJson(json) ?: throw IllegalStateException("Error parsing JSON")
    }
}