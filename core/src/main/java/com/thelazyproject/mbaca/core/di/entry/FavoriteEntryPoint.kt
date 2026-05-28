package com.thelazyproject.mbaca.core.di.entry

import com.thelazyproject.mbaca.core.domain.usecase.NovelUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteEntryPoint {
    fun novelUseCase(): NovelUseCase
}