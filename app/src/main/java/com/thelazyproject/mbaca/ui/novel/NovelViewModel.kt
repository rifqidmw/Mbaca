package com.thelazyproject.mbaca.ui.novel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thelazyproject.mbaca.core.data.Resource
import com.thelazyproject.mbaca.core.domain.model.Novel
import com.thelazyproject.mbaca.core.domain.usecase.NovelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NovelViewModel @Inject constructor(
    private val novelUseCase: NovelUseCase
) : ViewModel() {

    private val _novels = MutableLiveData<Resource<List<Novel>>>()
    val novels: LiveData<Resource<List<Novel>>> = _novels

    private val _loadMoreState = MutableLiveData<Resource<List<Novel>>>()
    val loadMoreState: LiveData<Resource<List<Novel>>> = _loadMoreState

    private var currentPage = 1
    private var currentQuery: String? = null
    private var isLoading = false
    private val allNovels = mutableListOf<Novel>()

    init {
        loadAllNovels()
    }

    fun loadAllNovels() {
        currentPage = 1
        currentQuery = null
        allNovels.clear()
        
        viewModelScope.launch {
            isLoading = true
            novelUseCase.getAllNovels().collectLatest { resource ->
                isLoading = false
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { novels ->
                            allNovels.clear()
                            allNovels.addAll(novels)
                            _novels.value = Resource.Success(allNovels.toList())
                        }
                    }
                    else -> _novels.value = resource
                }
            }
        }
    }

    fun searchNovels(query: String) {
        if (query.isBlank()) {
            loadAllNovels()
            return
        }

        currentPage = 1
        currentQuery = query
        allNovels.clear()

        viewModelScope.launch {
            isLoading = true
            novelUseCase.searchNovels(query, currentPage).collectLatest { resource ->
                isLoading = false
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { novels ->
                            allNovels.clear()
                            allNovels.addAll(novels)
                            _novels.value = Resource.Success(allNovels.toList())
                        }
                    }
                    else -> _novels.value = resource
                }
            }
        }
    }

    fun loadMoreNovels() {
        if (isLoading) return

        currentPage++

        viewModelScope.launch {
            isLoading = true
            
            val flow = if (currentQuery != null) {
                novelUseCase.searchNovels(currentQuery!!, currentPage)
            } else {
                novelUseCase.loadMoreNovels(currentPage)
            }

            flow.collectLatest { resource ->
                isLoading = false
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { newNovels ->
                            if (newNovels.isNotEmpty()) {
                                allNovels.addAll(newNovels)
                                _loadMoreState.value = Resource.Success(allNovels.toList())
                            } else {
                                // No more data
                                _loadMoreState.value = Resource.Success(allNovels.toList())
                            }
                        }
                    }
                    is Resource.Error -> {
                        currentPage-- // Revert page on error
                        _loadMoreState.value = resource
                    }
                    is Resource.Loading -> {
                        _loadMoreState.value = resource
                    }
                }
            }
        }
    }
}

