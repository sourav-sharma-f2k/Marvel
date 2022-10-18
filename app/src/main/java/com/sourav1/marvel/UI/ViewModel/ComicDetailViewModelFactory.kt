package com.sourav1.marvel.UI.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sourav1.marvel.Database.Entities.ComicsResult

class ComicDetailViewModelFactory(val result: ComicsResult): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ComicDetailViewModel(result) as T
    }
}