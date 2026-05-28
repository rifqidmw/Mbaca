package com.thelazyproject.mbaca.favorite

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.play.core.splitcompat.SplitCompat
import com.thelazyproject.mbaca.core.ui.NovelAdapter
import com.thelazyproject.mbaca.core.utils.NavigationHelper
import com.thelazyproject.mbaca.di.FavoriteEntryPoint
import com.thelazyproject.mbaca.favorite.databinding.ActivityFavoriteBinding
import dagger.hilt.android.EntryPointAccessors

class FavoriteActivity : AppCompatActivity() {


    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var novelAdapter: NovelAdapter

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.install(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val entryPoint = EntryPointAccessors.fromApplication(
            applicationContext,
            FavoriteEntryPoint::class.java
        )
        val factory = FavoriteViewModelFactory(entryPoint.novelUseCase())
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

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
