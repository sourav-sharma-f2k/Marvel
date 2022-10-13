package com.sourav1.marvel.Model.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sourav1.marvel.Model.Data.ComicsData.Result
import com.sourav1.marvel.R
import com.sourav1.marvel.Util.Constants

class CharacterDetailsAdapter :
    RecyclerView.Adapter<CharacterDetailsAdapter.CharacterDetailsViewHolder>() {

    inner class CharacterDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val comicTitleTv: TextView = itemView.findViewById(R.id.comicNameTv)
        val comicDescTv: TextView = itemView.findViewById(R.id.comicDescriptionTv)
        val comicThumbnailTv: ImageView = itemView.findViewById(R.id.comicThumbnailIV)
    }

    private val diffCallbacks = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallbacks)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterDetailsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comic, parent, false)
        return CharacterDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterDetailsViewHolder, position: Int) {
        val currRes = differ.currentList[position]
        holder.apply {
            comicTitleTv.text = currRes.title
            comicDescTv.text = if(currRes.description == null || currRes.description.isEmpty()){
                "Description is not provided by author."
            }else{
                currRes.description
            }

            val options =
                RequestOptions().placeholder(R.drawable.app_logo).error(R.drawable.app_logo)
            val imageUrl_ =
                "${currRes.thumbnail.path}/portrait_medium.${currRes.thumbnail.extension}"
            val imageUrl = Constants.convertHttpToHttps(imageUrl_)
            Glide.with(holder.itemView.context).load(imageUrl).apply(options).into(comicThumbnailTv)
        }
    }

    override fun getItemCount(): Int{
        return differ.currentList.size
    }
}