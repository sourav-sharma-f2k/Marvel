package com.sourav1.marvel.UI

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sourav1.marvel.Model.Adapter.CharacterDetailsAdapter
import com.sourav1.marvel.R
import com.sourav1.marvel.UI.ViewModel.LoadCharacterDetailsFactory
import com.sourav1.marvel.UI.ViewModel.LoadCharacterDetailsViewModel
import com.sourav1.marvel.Util.Constants.Companion.convertHttpToHttps

class CharacterDetails(val collectionURI: String, val mId: Int) :
    Fragment(R.layout.fragment_character_details) {
    lateinit var viewModel: LoadCharacterDetailsViewModel
    lateinit var rv: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var mAdapter: CharacterDetailsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv = view.findViewById(R.id.comicsRv)
        val mUrl = convertHttpToHttps(collectionURI)
        progressBar = view.findViewById(R.id.progressBarDetails)

        viewModel = ViewModelProvider(
            this,
            LoadCharacterDetailsFactory(mUrl, requireContext(), mId)
        )[LoadCharacterDetailsViewModel::class.java]
        setupRecyclerView()

        viewModel.result.observe(viewLifecycleOwner, Observer {
            when (it.size) {
                0 -> progressBar.visibility = View.VISIBLE
                else -> {
                    progressBar.visibility = View.GONE
                    mAdapter.differ.submitList(it)
                }
            }
        })

    }

    private fun setupRecyclerView() {
        mAdapter = CharacterDetailsAdapter()
        rv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }
    }
}