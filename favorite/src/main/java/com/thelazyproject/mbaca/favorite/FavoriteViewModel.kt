package com.thelazyproject.mbaca.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.thelazyproject.mbaca.core.domain.usecase.NovelUseCase

class FavoriteViewModel(novelUseCase: NovelUseCase) : ViewModel() {
    val favoriteNovels = novelUseCase.getFavoriteNovels().asLiveData()
}

