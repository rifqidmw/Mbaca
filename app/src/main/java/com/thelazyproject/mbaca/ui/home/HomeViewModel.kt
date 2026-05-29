package com.thelazyproject.mbaca.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.thelazyproject.mbaca.core.domain.usecase.NovelUseCase
import com.thelazyproject.mbaca.ui.model.toUiModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(novelUseCase: NovelUseCase) : ViewModel() {

    // Transform domain models to UI models
    val novels = novelUseCase.getAllNovels()
        .map { resource ->
            when (resource) {
                is com.thelazyproject.mbaca.core.data.Resource.Success -> {
                    com.thelazyproject.mbaca.core.data.Resource.Success(
                        resource.data?.toUiModels() ?: emptyList()
                    )
                }
                is com.thelazyproject.mbaca.core.data.Resource.Error -> {
                    com.thelazyproject.mbaca.core.data.Resource.Error(resource.message ?: "An error occurred")
                }
                is com.thelazyproject.mbaca.core.data.Resource.Loading -> {
                    com.thelazyproject.mbaca.core.data.Resource.Loading()
                }
            }
        }
        .asLiveData()
}

