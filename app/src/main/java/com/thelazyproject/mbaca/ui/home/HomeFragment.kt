package com.thelazyproject.mbaca.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.thelazyproject.mbaca.core.data.Resource
import com.thelazyproject.mbaca.core.utils.NavigationHelper
import com.thelazyproject.mbaca.databinding.FragmentHomeBinding
import com.thelazyproject.mbaca.ui.adapter.NovelUiAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var recentAdapter: NovelUiAdapter
    private lateinit var popularAdapter: NovelUiAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        observeNovels()
    }

    private fun setupRecyclerViews() {
        recentAdapter = NovelUiAdapter()
        binding.rvRecentNovels.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = recentAdapter
        }

        recentAdapter.onItemClick = { novel ->
            NavigationHelper.navigateToDetail(requireContext(), novel.id)
        }

        popularAdapter = NovelUiAdapter()
        binding.rvPopularNovels.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = popularAdapter
        }

        popularAdapter.onItemClick = { novel ->
            NavigationHelper.navigateToDetail(requireContext(), novel.id)
        }
    }

    private fun observeNovels() {
        viewModel.novels.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                    resource.data?.let { novels ->
                        recentAdapter.setData(novels.take(5))
                        popularAdapter.setData(novels.drop(5))
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = resource.message
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


