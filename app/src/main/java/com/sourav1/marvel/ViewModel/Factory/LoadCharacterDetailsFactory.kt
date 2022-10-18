package com.sourav1.marvel.ViewModel.Factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sourav1.marvel.ViewModel.LoadCharacterDetailsViewModel

class LoadCharacterDetailsFactory(val collectionURI: String, val context: Context, val id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoadCharacterDetailsViewModel(collectionURI, context, id) as T
    }
}
