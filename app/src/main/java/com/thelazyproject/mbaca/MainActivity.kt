package com.thelazyproject.mbaca

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.thelazyproject.mbaca.core.utils.NavigationHelper
import com.thelazyproject.mbaca.databinding.ActivityMainBinding
import com.thelazyproject.mbaca.ui.about.AboutFragment
import com.thelazyproject.mbaca.ui.home.HomeFragment
import com.thelazyproject.mbaca.ui.novel.NovelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        setupBottomNavigation()

        binding.fabFavorite.setOnClickListener {
            NavigationHelper.navigateToFavorite(this)
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null

            when (item.itemId) {
                R.id.navigation_home -> {
                    selectedFragment = HomeFragment()
                    binding.toolbar.title = "Mbaca"
                }
                R.id.navigation_novel -> {
                    selectedFragment = NovelFragment()
                    binding.toolbar.title = "Search Novels"
                }
                R.id.navigation_about -> {
                    selectedFragment = AboutFragment()
                    binding.toolbar.title = "About"
                }
            }

            selectedFragment?.let {
                loadFragment(it)
                true
            } ?: false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}

