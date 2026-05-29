package com.thelazyproject.mbaca.core.data.source.remote.network

import com.thelazyproject.mbaca.core.data.source.remote.response.BooksResponse
import com.thelazyproject.mbaca.core.data.source.remote.response.NovelResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("books/")
    suspend fun getNovels(
        @Query("page") page: Int = 1,
        @Query("search") search: String? = null,
        @Query("topic") topic: String? = "fiction"
    ): BooksResponse

    @GET("books/{id}/")
    suspend fun getNovelById(
        @Path("id") id: String
    ): NovelResponse
}

