package com.sourav1.marvel.Model.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sourav1.marvel.Data.Database.DbInstance
import com.sourav1.marvel.Data.Database.Entities.CharacterResult
import com.sourav1.marvel.R
import com.sourav1.marvel.Presentation.UI.CharacterDetails
import com.sourav1.marvel.Util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoadRecommendedCharacterAdapter(private val activity: FragmentActivity) :
    RecyclerView.Adapter<LoadRecommendedCharacterAdapter.LoadRecommendedCharacterAdapterViewHolder>() {

    inner class LoadRecommendedCharacterAdapterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val recommendedCharacterNameTv: TextView =
            itemView.findViewById(R.id.recommendedCharacterNameTv)
        val recommendedCharacterDescTv: TextView =
            itemView.findViewById(R.id.recommendedCharacterDescTv)
        val recommendedCharacterThumbnailIv: ImageView =
            itemView.findViewById(R.id.recommendedCharacterThumbnailIv)
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<CharacterResult>() {
        override fun areItemsTheSame(oldItem: CharacterResult, newItem: CharacterResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CharacterResult,
            newItem: CharacterResult
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LoadRecommendedCharacterAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recommended_character, parent, false)
        return LoadRecommendedCharacterAdapterViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: LoadRecommendedCharacterAdapterViewHolder,
        position: Int
    ) {
        val currRes = differ.currentList[position]
        val options = RequestOptions().placeholder(R.drawable.app_logo).error(R.drawable.app_logo)
            .centerCrop()
        holder.apply {
            recommendedCharacterNameTv.text = currRes.name
            recommendedCharacterDescTv.text =
                if (currRes.description == null || currRes.description == "") {
                    "Description not provided by author..."
                } else {
                    currRes.description
                }

            val imageUrl_ =
                "${currRes.thumbnailUrl}/portrait_xlarge.${currRes.thumbnailExtension}"
            val imageUrl = Constants.convertHttpToHttps(imageUrl_)
            Glide.with(holder.itemView.context).load(imageUrl).apply(options)
                .into(recommendedCharacterThumbnailIv)
        }

        holder.itemView.setOnClickListener {
            val args = Bundle()

            args.putString("URI", currRes.comicsListURI)
            args.putInt("PARENT_ID", currRes.id)
            args.putString("CHARACTER_NAME", currRes.name)

            val fragment = CharacterDetails()
            fragment.arguments = args

            activity.supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(null)
                commit()
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}