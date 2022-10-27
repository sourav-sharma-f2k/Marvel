package com.sourav1.marvel.Presentation.UI

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sourav1.marvel.Model.Adapter.LoadCharactersAdapter
import com.sourav1.marvel.Model.Adapter.LoadRecommendedCharacterAdapter
import com.sourav1.marvel.Presentation.ViewModel.LoadCharacterViewModel
import com.sourav1.marvel.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadCharacters : Fragment(R.layout.fragment_load_characters) {
    lateinit var viewModel: LoadCharacterViewModel
    private lateinit var mAdapter: LoadCharactersAdapter
    private lateinit var recommendedAdapter: LoadRecommendedCharacterAdapter
    private lateinit var recommendedRv: RecyclerView
    private lateinit var rv: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var recommendedCharTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Marvel Characters"

        setupOtherViews(view)
        setupRecommendedCharacterRv()
        setupRecyclerView()

        viewModel = ViewModelProvider(this)[LoadCharacterViewModel::class.java]
        viewModel.refreshRecommendedCharacters()

        viewModel.result.observe(viewLifecycleOwner) { result ->
            when (result.size) {
                0 -> progressBar.visibility = View.VISIBLE
                else -> {
                    progressBar.visibility = View.GONE
                    mAdapter.differ.submitList(result)
                }
            }
        }

        viewModel.resultRecommended.observe(viewLifecycleOwner) { result ->
            when (result.size) {
                0 -> {
                    recommendedRv.visibility = View.GONE
                    recommendedCharTv.visibility = View.GONE
                }
                else -> {
                    if (viewModel.recommendedClicked.value == true) {
                        recommendedRv.visibility = View.VISIBLE
                        recommendedCharTv.visibility = View.VISIBLE
                    }
                    recommendedAdapter.differ.submitList(result)
                }
            }
        }
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "inflater.inflate(R.menu.load_characters_menu, menu)",
            "com.sourav1.marvel.R"
        )
    )
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.load_characters_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.showRecommendedCharacters)
        item.title = viewModel.recommendedButtonText.value
        super.onPrepareOptionsMenu(menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.showRecommendedCharacters -> {
                if (viewModel.recommendedClicked.value == true) {
                    recommendedRv.visibility = View.GONE
                    recommendedCharTv.visibility = View.GONE
                    viewModel.recommendedButtonText.value = ("Show Recommended Characters")
                    viewModel.recommendedClicked.value = false
                } else {
                    recommendedRv.visibility = View.VISIBLE
                    recommendedCharTv.visibility = View.VISIBLE
                    viewModel.recommendedButtonText.value = ("Hide Recommended Characters")
                    viewModel.recommendedClicked.value = true
                }
                item.title = viewModel.recommendedButtonText.value
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupOtherViews(view: View) {
        rv = view.findViewById(R.id.characterRv)
        recommendedRv = view.findViewById(R.id.recommendedCharacterRv)
        progressBar = view.findViewById(R.id.progressBar)
        recommendedCharTv = view.findViewById(R.id.recommendedCharacterTv)
    }

    private fun setupRecommendedCharacterRv() {
        recommendedAdapter = LoadRecommendedCharacterAdapter(requireActivity())
        recommendedRv.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            adapter = recommendedAdapter
        }
    }

    private fun setupRecyclerView() {
        mAdapter = LoadCharactersAdapter() { id, args ->
            viewModel.increaseClick(id)

            val fragment = CharacterDetails()
            fragment.arguments = args

            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(null)
                commit()
            }
        }
        rv.apply {
            layoutManager = LinearLayoutManager(activity)
            scrollToPosition(0)
            adapter = mAdapter
        }
    }
}