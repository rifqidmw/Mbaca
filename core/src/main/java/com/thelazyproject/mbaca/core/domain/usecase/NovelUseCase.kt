package com.thelazyproject.mbaca.core.domain.usecase

import com.thelazyproject.mbaca.core.data.Resource
import com.thelazyproject.mbaca.core.domain.model.Novel
import com.thelazyproject.mbaca.core.domain.repository.INovelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NovelUseCase @Inject constructor(private val novelRepository: INovelRepository) {

    fun getAllNovels(): Flow<Resource<List<Novel>>> = novelRepository.getAllNovels()

    fun searchNovels(query: String, page: Int): Flow<Resource<List<Novel>>> =
        novelRepository.searchNovels(query, page)

    fun loadMoreNovels(page: Int): Flow<Resource<List<Novel>>> =
        novelRepository.loadMoreNovels(page)

    fun getFavoriteNovels(): Flow<List<Novel>> = novelRepository.getFavoriteNovels()

    fun getNovelById(id: String): Flow<Novel> = novelRepository.getNovelById(id)

    suspend fun setFavoriteNovel(id: String, isFavorite: Boolean) =
        novelRepository.setFavoriteNovel(id, isFavorite)
}

