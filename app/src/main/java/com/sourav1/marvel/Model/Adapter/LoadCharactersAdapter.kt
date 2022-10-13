package com.sourav1.marvel.Model.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sourav1.marvel.Model.Data.CharacterData.Result
import com.sourav1.marvel.R
import com.sourav1.marvel.UI.CharacterDetails
import com.sourav1.marvel.UI.LoadCharacters
import com.sourav1.marvel.Util.Constants.Companion.convertHttpToHttps

class LoadCharactersAdapter(private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<LoadCharactersAdapter.LoadCharacterViewHolder>() {

    inner class LoadCharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val characterNameTv: TextView = itemView.findViewById(R.id.characterNameTv)
        val descriptionTv: TextView = itemView.findViewById(R.id.descriptionTv)
        val thumbnailIv: ImageView = itemView.findViewById(R.id.thumbnailIV)

        override fun onClick(view: View?) {
            if(view != null){
                itemView.setOnClickListener(this)
            }
        }
    }

    private val diffCallBacks = object : DiffUtil.ItemCallback<Result>() {
        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }
    }

    val differ = AsyncListDiffer(this, diffCallBacks)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadCharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_character, parent, false
        )
        return LoadCharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoadCharacterViewHolder, position: Int) {
        val currRes = differ.currentList[position]
        val options = RequestOptions().placeholder(R.drawable.app_logo).error(R.drawable.app_logo)
        holder.apply {
            characterNameTv.text = currRes.name
            descriptionTv.text = if (currRes.description.isNotEmpty()) {
                currRes.description
            } else {
                "No description provided 🥲"
            }

            val imageUrl_ =
                "${currRes.thumbnail.path}/portrait_medium.${currRes.thumbnail.extension}"
            val imageUrl = convertHttpToHttps(imageUrl_)
            Glide.with(holder.itemView.context).load(imageUrl).apply(options).into(thumbnailIv)
        }
        holder.itemView.setOnClickListener {
            fragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, CharacterDetails(currRes.comics.collectionURI))
                addToBackStack(null)
                commit()
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}