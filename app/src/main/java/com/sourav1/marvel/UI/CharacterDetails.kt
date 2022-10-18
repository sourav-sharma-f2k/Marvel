package com.sourav1.marvel.UI

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sourav1.marvel.Model.Adapter.CharacterDetailsAdapter
import com.sourav1.marvel.R
import com.sourav1.marvel.ViewModel.Factory.LoadCharacterDetailsFactory
import com.sourav1.marvel.ViewModel.LoadCharacterDetailsViewModel
import com.sourav1.marvel.Util.Constants.Companion.convertHttpToHttps

class CharacterDetails() :
    Fragment(R.layout.fragment_character_details) {
    lateinit var viewModel: LoadCharacterDetailsViewModel
    lateinit var rv: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var mAdapter: CharacterDetailsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val collectionURI = requireArguments().getString("URI")
        val mId = requireArguments().getInt("PARENT_ID")
        val characterName = requireArguments().getString("CHARACTER_NAME")

        (activity as AppCompatActivity).supportActionBar?.title = "${characterName.toString()} featured comics"

        rv = view.findViewById(R.id.comicsRv)
        val mUrl = convertHttpToHttps(collectionURI!!)
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
        mAdapter = CharacterDetailsAdapter(requireActivity().supportFragmentManager)
        rv.apply {
            layoutManager =
                LinearLayoutManager(
                    requireActivity().applicationContext
                )
            adapter = mAdapter
        }
    }
}