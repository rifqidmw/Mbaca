package com.thelazyproject.mbaca.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.thelazyproject.mbaca.core.domain.usecase.NovelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailNovelViewModel @Inject constructor(
    private val novelUseCase: NovelUseCase
) : ViewModel() {

    fun getNovelById(id: String) = novelUseCase.getNovelById(id).asLiveData()

    fun setFavoriteNovel(id: String, isFavorite: Boolean) {
        viewModelScope.launch {
            novelUseCase.setFavoriteNovel(id, isFavorite)
        }
    }
}

