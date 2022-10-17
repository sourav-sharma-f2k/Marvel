package com.sourav1.marvel.UI

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sourav1.marvel.Model.Adapter.LoadCharactersAdapter
import com.sourav1.marvel.R
import com.sourav1.marvel.UI.ViewModel.LoadCharacterFactory
import com.sourav1.marvel.UI.ViewModel.LoadCharacterViewModel

class LoadCharacters : Fragment(R.layout.fragment_load_characters) {
    lateinit var viewModel: LoadCharacterViewModel
    lateinit var mAdapter: LoadCharactersAdapter
    lateinit var rv: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            LoadCharacterFactory(requireContext())
        )[LoadCharacterViewModel::class.java]

        rv = view.findViewById(R.id.characterRv)
        progressBar = view.findViewById(R.id.progressBar)

        setupRecyclerView()

        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            when (result.size) {
                0 -> progressBar.visibility = View.VISIBLE
                else -> {
                    progressBar.visibility = View.GONE
                    mAdapter.differ.submitList(result)
                }
            }
        })
    }

    private fun setupRecyclerView() {
        mAdapter = LoadCharactersAdapter(requireActivity().supportFragmentManager)
        rv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
    }
}