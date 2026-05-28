package com.thelazyproject.mbaca.core.data.source.remote

import com.thelazyproject.mbaca.core.data.source.remote.network.ApiService
import com.thelazyproject.mbaca.core.data.source.remote.response.NovelResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getNovels(page: Int = 1): List<NovelResponse> {
        val response = apiService.getNovels(page = page)
        return response.results ?: emptyList()
    }

    suspend fun searchNovels(query: String, page: Int = 1): List<NovelResponse> {
        val response = apiService.getNovels(page = page, search = query, topic = null)
        return response.results ?: emptyList()
    }
}

