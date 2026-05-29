package com.thelazyproject.mbaca.core.data.source.local

import com.thelazyproject.mbaca.core.data.source.local.entity.NovelEntity
import com.thelazyproject.mbaca.core.data.source.local.room.NovelDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val novelDao: NovelDao) {

    fun getAllNovels(): Flow<List<NovelEntity>> = novelDao.getAllNovels()

    fun getFavoriteNovels(): Flow<List<NovelEntity>> = novelDao.getFavoriteNovels()

    fun getNovelById(id: String): Flow<NovelEntity?> = novelDao.getNovelById(id)

    suspend fun insertNovels(novels: List<NovelEntity>) = novelDao.insertNovels(novels)

    suspend fun updateFavoriteStatus(id: String, isFavorite: Boolean) =
        novelDao.updateFavoriteStatus(id, isFavorite)
}

