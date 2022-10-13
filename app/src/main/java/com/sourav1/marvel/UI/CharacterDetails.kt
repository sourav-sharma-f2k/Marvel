package com.sourav1.marvel.UI

import android.os.Bundle
import android.view.View
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

class CharacterDetails(val collectionURI: String) : Fragment(R.layout.fragment_character_details) {
    lateinit var viewModel: LoadCharacterDetailsViewModel
    lateinit var rv: RecyclerView
    lateinit var mAdapter: CharacterDetailsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv = view.findViewById(R.id.comicsRv)
        val mUrl = convertHttpToHttps(collectionURI)

        viewModel = ViewModelProvider(
            this,
            LoadCharacterDetailsFactory(mUrl)
        )[LoadCharacterDetailsViewModel::class.java]
        setupRecyclerView()

        viewModel.comicsResult.observe(viewLifecycleOwner, Observer {
            mAdapter.differ.submitList(it)
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