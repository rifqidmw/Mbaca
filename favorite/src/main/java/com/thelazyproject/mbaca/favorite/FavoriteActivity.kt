package com.thelazyproject.mbaca.favorite

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.thelazyproject.mbaca.core.ui.NovelAdapter
import com.thelazyproject.mbaca.core.utils.NavigationHelper
import com.thelazyproject.mbaca.favorite.databinding.ActivityFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var novelAdapter: NovelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite Novels"

        binding.toolbar.navigationIcon?.setTint(
            ContextCompat.getColor(this, android.R.color.white)
        )

        setupRecyclerView()
        observeFavoriteNovels()
    }

    private fun setupRecyclerView() {
        novelAdapter = NovelAdapter()
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = novelAdapter
        }

        novelAdapter.onItemClick = { novel ->
            NavigationHelper.navigateToDetail(this@FavoriteActivity, novel.id)
        }
    }

    private fun observeFavoriteNovels() {
        viewModel.favoriteNovels.observe(this) { novels ->
            if (novels.isEmpty()) {
                binding.rvFavorite.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                binding.rvFavorite.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.GONE
                novelAdapter.setData(novels)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

