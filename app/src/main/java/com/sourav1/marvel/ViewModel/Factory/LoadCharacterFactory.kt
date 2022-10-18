package com.sourav1.marvel.ViewModel.Factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sourav1.marvel.ViewModel.LoadCharacterViewModel

class LoadCharacterFactory(val context: Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoadCharacterViewModel(context) as T
    }
}