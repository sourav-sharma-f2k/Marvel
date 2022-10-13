package com.sourav1.marvel.UI.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoadCharacterDetailsFactory(val collectionURI: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoadCharacterDetailsViewModel(collectionURI) as T
    }
}
