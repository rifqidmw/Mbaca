package com.thelazyproject.mbaca.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.thelazyproject.mbaca.core.domain.usecase.NovelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(novelUseCase: NovelUseCase) : ViewModel() {
    val novels = novelUseCase.getAllNovels().asLiveData()
}

