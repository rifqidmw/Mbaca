package com.thelazyproject.mbaca.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thelazyproject.mbaca.databinding.FragmentAboutBinding

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

        binding.apply {
            tvAppName.text = "Mbaca"
            tvVersion.text = "v1.0"

            tvFeatures.text = """
                → Browse classic literature
                → Search by title or author
                → Save favorites for later
                → Read book details and descriptions
            """.trimIndent()

            tvTechStack.text = """
                Built with:
                Kotlin • Room • Retrofit • Hilt
                MVVM • Coroutines • Flow
            """.trimIndent()

            tvApiInfo.text = "Books from gutendex.com"
            tvDeveloper.text = "Made by The Lazy Project"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

