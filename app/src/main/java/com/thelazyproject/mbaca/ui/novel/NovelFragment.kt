package com.thelazyproject.mbaca.ui.novel

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thelazyproject.mbaca.core.data.Resource
import com.thelazyproject.mbaca.core.utils.NavigationHelper
import com.thelazyproject.mbaca.databinding.FragmentNovelBinding
import com.thelazyproject.mbaca.ui.adapter.NovelUiAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NovelFragment : Fragment() {

    private var _binding: FragmentNovelBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NovelViewModel by viewModels()
    private lateinit var novelAdapter: NovelUiAdapter
    private var isLoadingMore = false
    private var scrollListener: RecyclerView.OnScrollListener? = null
    private var textWatcher: TextWatcher? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNovelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupReactiveSearch()
        observeNovels()
        observeLoadMore()
    }

    private fun setupRecyclerView() {
        novelAdapter = NovelUiAdapter()
        val layoutManager = LinearLayoutManager(requireContext())

        scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoadingMore && dy > 0) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 3
                        && firstVisibleItemPosition >= 0) {
                        isLoadingMore = true
                        viewModel.loadMoreNovels()
                    }
                }
            }
        }

        binding.rvNovels.apply {
            this.layoutManager = layoutManager
            adapter = novelAdapter
            scrollListener?.let { addOnScrollListener(it) }
        }

        novelAdapter.onItemClick = { novel ->
            NavigationHelper.navigateToDetail(requireContext(), novel.id)
        }
    }

    private fun setupReactiveSearch() {
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.ivClearSearch.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
                viewModel.updateSearchQuery(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.etSearch.addTextChangedListener(textWatcher)

        binding.etSearch.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                (event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                val query = binding.etSearch.text.toString()
                if (query.isNotBlank()) {
                    viewModel.searchNovels(query)
                    binding.etSearch.clearFocus()
                }
                true
            } else {
                false
            }
        }


        binding.ivClearSearch.setOnClickListener {
            binding.etSearch.text?.clear()
            viewModel.loadAllNovels()
        }
    }

    private fun observeNovels() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.novels.collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.rvNovels.visibility = View.GONE
                            binding.tvError.visibility = View.GONE
                            binding.tvNoResults.visibility = View.GONE
                        }
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.tvError.visibility = View.GONE

                            resource.data?.let { novels ->
                                if (novels.isEmpty()) {
                                    binding.tvNoResults.visibility = View.VISIBLE
                                    binding.rvNovels.visibility = View.GONE
                                } else {
                                    binding.tvNoResults.visibility = View.GONE
                                    binding.rvNovels.visibility = View.VISIBLE
                                    novelAdapter.setData(novels)
                                }
                            }
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.rvNovels.visibility = View.GONE
                            binding.tvNoResults.visibility = View.GONE
                            binding.tvError.visibility = View.VISIBLE
                            binding.tvError.text = resource.message
                        }
                    }
                }
            }
        }
    }

    private fun observeLoadMore() {
        viewModel.loadMoreState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    isLoadingMore = true
                    novelAdapter.showLoading()
                }
                is Resource.Success -> {
                    isLoadingMore = false
                    novelAdapter.hideLoading()
                    resource.data?.let { novels ->
                        novelAdapter.setData(novels)
                    }
                }
                is Resource.Error -> {
                    isLoadingMore = false
                    novelAdapter.hideLoading()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        textWatcher?.let { binding.etSearch.removeTextChangedListener(it) }
        textWatcher = null
        scrollListener?.let { binding.rvNovels.removeOnScrollListener(it) }
        scrollListener = null
        novelAdapter.onItemClick = null
        _binding = null
    }
}

