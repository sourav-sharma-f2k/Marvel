package com.sourav1.marvel.Presentation.UI

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sourav1.marvel.Data.Database.Entities.ComicsResult
import com.sourav1.marvel.R
import com.sourav1.marvel.Presentation.ViewModel.ComicDetailViewModel
import com.sourav1.marvel.Presentation.ViewModel.Factory.ComicDetailViewModelFactory
import com.sourav1.marvel.Util.Constants.Companion.convertHttpToHttps

class ComicDetails() : Fragment(R.layout.fragment_comic_details) {

    lateinit var titleTv: TextView
    lateinit var descriptionTv: TextView
    lateinit var thumbnailIv: ImageView
    lateinit var purchaseBtn: Button
    lateinit var pageCountTv: TextView
    lateinit var printPriceTv: TextView
    lateinit var digitalPurchasePriceTv: TextView
    lateinit var viewModel: ComicDetailViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val comicsResult = requireArguments().getSerializable("COMICS_RESULT") as ComicsResult?
        (activity as AppCompatActivity).supportActionBar?.title = "Comic Details"

        viewModel = ViewModelProvider(
            this,
            ComicDetailViewModelFactory(comicsResult!!)
        )[ComicDetailViewModel::class.java]
        initializeViews(view)


        viewModel.result.observe(viewLifecycleOwner, Observer {
            loadValues(it)
        })

        val purchaseUrl = comicsResult.detailsUrl

        purchaseBtn.setOnClickListener {
            if (purchaseUrl == null || purchaseUrl == "") {
                Toast.makeText(
                    activity,
                    "Sorry this book is no longer available...",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val args = Bundle()
                args.putString("PURCHASE_URL", purchaseUrl)
                val fragment = ComicPurchaseScreen()
                fragment.arguments = args
                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.fragmentContainer, fragment)
                    commit()
                }
            }
        }
    }

    private fun initializeViews(view: View) {
        titleTv = view.findViewById(R.id.comicDetailNameTv)
        descriptionTv = view.findViewById(R.id.comicDetailDescriptionTv)
        thumbnailIv = view.findViewById(R.id.comicDetailThumbnailIv)
        purchaseBtn = view.findViewById(R.id.comicPurchaseButton)
        pageCountTv = view.findViewById(R.id.pageCountTv)
        printPriceTv = view.findViewById(R.id.printPriceTv)
        digitalPurchasePriceTv = view.findViewById(R.id.digitalPurchasePriceTv)
    }

    private fun loadValues(comicsResult: ComicsResult) {
        val title = comicsResult.title
        val description = if (comicsResult.description == null || comicsResult.description == "") {
            "Description not provided by author..."
        } else {
            comicsResult.description
        }
        val url = "${comicsResult.thumbnailUrl}/landscape_xlarge.${comicsResult.thumbnailExtension}"
        val thumbnailUrl = convertHttpToHttps(url)
        val printPrice = "Print price: ${comicsResult.printPrice} $"
        val digitalPurchasePrice = "Digital purchase price: ${comicsResult.digitalPurchasePrice} $"
        val pageCount = "No of pages: ${comicsResult.pageCount}"

        titleTv.text = title
        descriptionTv.text = description

        val options = RequestOptions().placeholder(R.drawable.app_logo).error(R.drawable.app_logo)
        Glide.with(requireContext()).load(thumbnailUrl).apply(options).into(thumbnailIv)
        pageCountTv.text = pageCount
        printPriceTv.text = printPrice
        digitalPurchasePriceTv.text = digitalPurchasePrice
    }
}