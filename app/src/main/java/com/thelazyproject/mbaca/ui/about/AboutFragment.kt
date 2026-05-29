package com.thelazyproject.mbaca.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.thelazyproject.mbaca.BuildConfig
import com.thelazyproject.mbaca.databinding.FragmentAboutBinding
import androidx.core.net.toUri
import com.thelazyproject.mbaca.R

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAboutInfo()
        setupClickListeners()
    }

    private fun setupAboutInfo() {
        binding.apply {
            tvAppName.text = getString(R.string.app_name)
            tvVersion.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)
            tvBuildType.text = getString(R.string.app_build_tyoe, BuildConfig.BUILD_TYPE)

            tvFeatures.text = getString(R.string.features).trimIndent()

            tvTechStack.text = getString(R.string.tech_stack).trimIndent()

            tvApiInfo.text = getString(R.string.api_info)
            tvDeveloper.text = getString(R.string.developer)
            tvDescription.text = getString(R.string.app_description)
        }
    }

    private fun setupClickListeners() {
        binding.apply {
            btnShareApp.setOnClickListener { shareApp() }
            btnRateApp.setOnClickListener { rateApp() }
            btnViewApi.setOnClickListener { openUrl("https://gutendex.com") }
            btnGithub.setOnClickListener {
                openUrl("https://github.com/rifqidmw/Mbaca")
            }
            btnLibraries.setOnClickListener { showLibrariesDialog() }
        }
    }

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Mbaca App")
                putExtra(Intent.EXTRA_TEXT, "Check out Mbaca - read classic books from Project Gutenberg for free!")
            }
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Can't share right now", Toast.LENGTH_SHORT).show()
        }
    }

    private fun rateApp() {
        try {
            val packageName = requireContext().packageName
            val uri = "market://details?id=$packageName".toUri()
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            try {
                val packageName = requireContext().packageName
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        "https://play.google.com/store/apps/details?id=$packageName".toUri()
                    )
                )
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Can't open Play Store", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Can't open URL", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLibrariesDialog() {
        val libraries = """
            AndroidX (Core, AppCompat, Material)
            Lifecycle (ViewModel, LiveData)
            Room Database
            Navigation Component
            Dagger Hilt
            Retrofit + OkHttp
            Glide
            Kotlin Coroutines + Flow
            Play Core
        """.trimIndent()

        AlertDialog.Builder(requireContext())
            .setTitle("Libraries Used")
            .setMessage(libraries)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

