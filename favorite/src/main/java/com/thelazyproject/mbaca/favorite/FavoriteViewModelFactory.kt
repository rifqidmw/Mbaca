package com.thelazyproject.mbaca.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thelazyproject.mbaca.core.domain.usecase.NovelUseCase

class FavoriteViewModelFactory(
    private val novelUseCase: NovelUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(novelUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

