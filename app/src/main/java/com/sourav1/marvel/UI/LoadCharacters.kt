package com.sourav1.marvel.UI

import android.os.Bundle
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
import com.sourav1.marvel.R
import com.sourav1.marvel.ViewModel.Factory.LoadCharacterFactory
import com.sourav1.marvel.ViewModel.LoadCharacterViewModel

class LoadCharacters : Fragment(R.layout.fragment_load_characters) {
    lateinit var viewModel: LoadCharacterViewModel
    private lateinit var mAdapter: LoadCharactersAdapter
    private lateinit var recommendedAdapter: LoadRecommendedCharacterAdapter
    private lateinit var recommendedRv: RecyclerView
    private lateinit var rv: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var recommendedCharTv: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Marvel Characters"

        setupOtherViews(view)
        setupRecommendedCharacterRv()
        setupRecyclerView()

        viewModel = ViewModelProvider(
            this,
            LoadCharacterFactory(requireContext())
        )[LoadCharacterViewModel::class.java]

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
                    recommendedRv.visibility = View.VISIBLE
                    recommendedCharTv.visibility = View.VISIBLE
                    recommendedAdapter.differ.submitList(result)
                }
            }
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
        mAdapter = LoadCharactersAdapter(requireActivity())
        rv.apply {
            layoutManager = LinearLayoutManager(activity)
            scrollToPosition(0)
            adapter = mAdapter
        }
    }
}