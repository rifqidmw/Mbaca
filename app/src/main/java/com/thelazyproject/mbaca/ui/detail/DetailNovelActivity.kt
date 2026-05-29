package com.thelazyproject.mbaca.ui.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.thelazyproject.mbaca.R
import com.thelazyproject.mbaca.core.data.Resource
import com.thelazyproject.mbaca.core.utils.NavigationHelper
import com.thelazyproject.mbaca.databinding.ActivityDetailNovelBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailNovelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailNovelBinding
    private val viewModel: DetailNovelViewModel by viewModels()
    private var novelId: String = ""
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNovelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.white))

        novelId = intent.getStringExtra(NavigationHelper.EXTRA_NOVEL_ID) ?: ""

        observeNovelDetail()

        binding.fabFavorite.setOnClickListener {
            isFavorite = !isFavorite
            viewModel.setFavoriteNovel(novelId, isFavorite)
            updateFavoriteIcon()
        }
    }

    private fun observeNovelDetail() {
        viewModel.getNovelDetailFromRemote(novelId).observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.contentLayout.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.contentLayout.visibility = View.VISIBLE

                    resource.data?.let { novel ->
                        binding.apply {
                            tvTitle.text = novel.title
                            tvAuthor.text = novel.author
                            tvCategory.text = novel.category
                            tvPublished.text = novel.publishedDate
                            tvPages.text = novel.pageCount.toString()
                            tvRating.text = novel.rating.toString()
                            tvDescription.text = novel.description

                            Glide.with(this@DetailNovelActivity)
                                .load(novel.image)
                                .placeholder(android.R.drawable.ic_menu_gallery)
                                .error(android.R.drawable.ic_menu_gallery)
                                .into(ivCover)

                            isFavorite = novel.isFavorite
                            updateFavoriteIcon()
                        }
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.contentLayout.visibility = View.GONE
                    Toast.makeText(
                        this,
                        resource.message ?: "Failed to load book details",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
    }

    private fun updateFavoriteIcon() {
        if (isFavorite) {
            binding.fabFavorite.setImageResource(android.R.drawable.star_big_on)
            binding.fabFavorite.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_secondary)
            )
            binding.fabFavorite.imageTintList = ColorStateList.valueOf(Color.WHITE)
        } else {
            binding.fabFavorite.setImageResource(android.R.drawable.star_big_off)
            binding.fabFavorite.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_surface)
            )
            binding.fabFavorite.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_on_surface)
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

