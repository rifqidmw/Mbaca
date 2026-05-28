package com.thelazyproject.mbaca.core.data.source.remote.network

import com.thelazyproject.mbaca.core.data.source.remote.response.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("books/")
    suspend fun getNovels(
        @Query("page") page: Int = 1,
        @Query("search") search: String? = null,
        @Query("topic") topic: String? = "fiction"
    ): BooksResponse
}

