package com.thelazyproject.mbaca.core.domain.repository

import com.thelazyproject.mbaca.core.data.Resource
import com.thelazyproject.mbaca.core.domain.model.Novel
import kotlinx.coroutines.flow.Flow

interface INovelRepository {
    fun getAllNovels(): Flow<Resource<List<Novel>>>
    fun searchNovels(query: String, page: Int): Flow<Resource<List<Novel>>>
    fun loadMoreNovels(page: Int): Flow<Resource<List<Novel>>>
    fun getFavoriteNovels(): Flow<List<Novel>>
    fun getNovelById(id: String): Flow<Novel?>
    fun getNovelDetailFromRemote(id: String): Flow<Resource<Novel>>
    suspend fun setFavoriteNovel(id: String, isFavorite: Boolean)
}

