package com.thelazyproject.mbaca.core.data

import com.thelazyproject.mbaca.core.data.source.local.LocalDataSource
import com.thelazyproject.mbaca.core.data.source.remote.RemoteDataSource
import com.thelazyproject.mbaca.core.domain.model.Novel
import com.thelazyproject.mbaca.core.domain.repository.INovelRepository
import com.thelazyproject.mbaca.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NovelRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : INovelRepository {

    override fun getAllNovels(): Flow<Resource<List<Novel>>> = flow {
        emit(Resource.Loading())

        try {
            val localData = localDataSource.getAllNovels().first()

            if (localData.isEmpty()) {
                val remoteData = remoteDataSource.getNovels(page = 1)
                val novelEntities = DataMapper.mapResponsesToEntities(remoteData)
                localDataSource.insertNovels(novelEntities)
            }

            val novelsFlow = localDataSource.getAllNovels()
                .map { DataMapper.mapEntitiesToDomain(it) }

            novelsFlow.collect { novels ->
                emit(Resource.Success(novels))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }

    override fun searchNovels(query: String, page: Int): Flow<Resource<List<Novel>>> = flow {
        emit(Resource.Loading())

        try {
            val remoteData = remoteDataSource.searchNovels(query, page)
            val novels = DataMapper.mapResponsesToDomain(remoteData)
            emit(Resource.Success(novels))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }

    override fun loadMoreNovels(page: Int): Flow<Resource<List<Novel>>> = flow {
        emit(Resource.Loading())

        try {
            val remoteData = remoteDataSource.getNovels(page)
            val novels = DataMapper.mapResponsesToDomain(remoteData)
            emit(Resource.Success(novels))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }

    override fun getFavoriteNovels(): Flow<List<Novel>> {
        return localDataSource.getFavoriteNovels()
            .map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun getNovelById(id: String): Flow<Novel> {
        return localDataSource.getNovelById(id)
            .map { DataMapper.mapEntityToDomain(it) }
    }

    override suspend fun setFavoriteNovel(id: String, isFavorite: Boolean) {
        localDataSource.updateFavoriteStatus(id, isFavorite)
    }
}

