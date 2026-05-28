package com.thelazyproject.mbaca.core.di

import com.thelazyproject.mbaca.core.data.NovelRepository
import com.thelazyproject.mbaca.core.domain.repository.INovelRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideNovelRepository(novelRepository: NovelRepository): INovelRepository
}

