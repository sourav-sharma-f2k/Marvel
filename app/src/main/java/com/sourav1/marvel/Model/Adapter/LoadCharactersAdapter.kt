package com.sourav1.marvel.Model.Adapter

import android.os.Bundle
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
import com.sourav1.marvel.Database.Entities.CharacterResult
import com.sourav1.marvel.R
import com.sourav1.marvel.UI.CharacterDetails
import com.sourav1.marvel.Util.Constants.Companion.convertHttpToHttps

class LoadCharactersAdapter(private val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<LoadCharactersAdapter.LoadCharacterViewHolder>() {

    inner class LoadCharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val characterNameTv: TextView = itemView.findViewById(R.id.characterNameTv)
        val descriptionTv: TextView = itemView.findViewById(R.id.descriptionTv)
        val thumbnailIv: ImageView = itemView.findViewById(R.id.thumbnailIV)

        override fun onClick(view: View?) {
            if (view != null) {
                itemView.setOnClickListener(this)
            }
        }
    }

    private val diffCallBacks = object : DiffUtil.ItemCallback<CharacterResult>() {
        override fun areContentsTheSame(oldItem: CharacterResult, newItem: CharacterResult): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: CharacterResult, newItem: CharacterResult): Boolean {
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
        val options = RequestOptions().placeholder(R.drawable.app_logo).error(R.drawable.app_logo).centerCrop()
        holder.apply {
            characterNameTv.text = currRes.name
            descriptionTv.text = if(currRes.description == null || currRes.description == ""){
                "Description not provided by author..."
            }
            else{
                currRes.description
            }

            val imageUrl_ =
                "${currRes.thumbnailUrl}/portrait_xlarge.${currRes.thumbnailExtension}"
            val imageUrl = convertHttpToHttps(imageUrl_)
            Glide.with(holder.itemView.context).load(imageUrl).apply(options).into(thumbnailIv)
        }
        holder.itemView.setOnClickListener {
            val args = Bundle()
            args.putString("URI", currRes.comicsListURI)
            args.putInt("PARENT_ID", currRes.id)
            val fragment = CharacterDetails()
            fragment.arguments = args
            fragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainer, fragment)
                addToBackStack(null)
                commit()
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}