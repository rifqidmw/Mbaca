package com.thelazyproject.mbaca.ui.novel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thelazyproject.mbaca.core.data.Resource
import com.thelazyproject.mbaca.core.domain.usecase.NovelUseCase
import com.thelazyproject.mbaca.ui.model.NovelUiModel
import com.thelazyproject.mbaca.ui.model.toUiModels
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NovelViewModel @Inject constructor(
    private val novelUseCase: NovelUseCase
) : ViewModel() {

    private val _novels = MutableStateFlow<Resource<List<NovelUiModel>>>(Resource.Loading())
    val novels: StateFlow<Resource<List<NovelUiModel>>> = _novels.asStateFlow()

    private val _loadMoreState = MutableLiveData<Resource<List<NovelUiModel>>>()
    val loadMoreState: LiveData<Resource<List<NovelUiModel>>> = _loadMoreState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var currentPage = 1
    private var currentQuery: String? = null
    private var isLoading = false
    private val allNovels = mutableListOf<NovelUiModel>()

    init {
        loadAllNovels()
        setupReactiveSearch()
    }

    @OptIn(FlowPreview::class)
    private fun setupReactiveSearch() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .filter { it.length >= 3 || it.isEmpty() }
                .collectLatest { query ->
                    if (query.isEmpty()) {
                        loadAllNovels()
                    } else {
                        performSearch(query)
                    }
                }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun loadAllNovels() {
        currentPage = 1
        currentQuery = null
        allNovels.clear()
        
        viewModelScope.launch {
            isLoading = true
            _novels.value = Resource.Loading()

            novelUseCase.getAllNovels().collectLatest { resource ->
                isLoading = false
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { novels ->
                            allNovels.clear()
                            allNovels.addAll(novels.toUiModels())
                            _novels.value = Resource.Success(allNovels.toList())
                        }
                    }
                    is Resource.Error -> {
                        _novels.value = Resource.Error(resource.message ?: "An error occurred")
                    }
                    is Resource.Loading -> {
                        _novels.value = Resource.Loading()
                    }
                }
            }
        }
    }

    private fun performSearch(query: String) {
        currentPage = 1
        currentQuery = query
        allNovels.clear()

        viewModelScope.launch {
            isLoading = true
            _novels.value = Resource.Loading()

            novelUseCase.searchNovels(query, currentPage).collectLatest { resource ->
                isLoading = false
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { novels ->
                            allNovels.clear()
                            allNovels.addAll(novels.toUiModels())
                            _novels.value = Resource.Success(allNovels.toList())
                        }
                    }
                    is Resource.Error -> {
                        _novels.value = Resource.Error(resource.message ?: "An error occurred")
                    }
                    is Resource.Loading -> {
                        _novels.value = Resource.Loading()
                    }
                }
            }
        }
    }

    fun searchNovels(query: String) {
        _searchQuery.value = query
    }

    fun loadMoreNovels() {
        if (isLoading) return

        currentPage++

        viewModelScope.launch {
            isLoading = true
            _loadMoreState.value = Resource.Loading()

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
                                allNovels.addAll(newNovels.toUiModels())
                                _loadMoreState.value = Resource.Success(allNovels.toList())
                            } else {
                                _loadMoreState.value = Resource.Success(allNovels.toList())
                            }
                        }
                    }
                    is Resource.Error -> {
                        currentPage--
                        _loadMoreState.value = Resource.Error(resource.message ?: "An error occurred")
                    }
                    is Resource.Loading -> {
                        _loadMoreState.value = Resource.Loading()
                    }
                }
            }
        }
    }
}

