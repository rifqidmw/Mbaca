package com.thelazyproject.mbaca.ui.detail

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.thelazyproject.mbaca.R
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
        viewModel.getNovelById(novelId).observe(this) { novel ->
            novel?.let {
                binding.apply {
                    tvTitle.text = it.title
                    tvAuthor.text = it.author
                    tvCategory.text = it.category
                    tvPublished.text = it.publishedDate
                    tvPages.text = it.pageCount.toString()
                    tvRating.text = it.rating.toString()
                    tvDescription.text = it.description

                    Glide.with(this@DetailNovelActivity)
                        .load(it.image)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(ivCover)

                    isFavorite = it.isFavorite
                    updateFavoriteIcon()
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

