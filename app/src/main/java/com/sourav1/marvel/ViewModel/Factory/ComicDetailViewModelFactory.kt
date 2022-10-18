package com.sourav1.marvel.ViewModel.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sourav1.marvel.Database.Entities.ComicsResult
import com.sourav1.marvel.ViewModel.ComicDetailViewModel

class ComicDetailViewModelFactory(val result: ComicsResult): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ComicDetailViewModel(result) as T
    }
}