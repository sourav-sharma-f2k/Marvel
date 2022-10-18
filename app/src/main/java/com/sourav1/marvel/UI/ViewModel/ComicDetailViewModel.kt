package com.sourav1.marvel.UI.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sourav1.marvel.Database.Entities.ComicsResult

class ComicDetailViewModel(val comicsResult: ComicsResult): ViewModel() {
    val result: MutableLiveData<ComicsResult> = MutableLiveData()

    init {
        result.postValue(comicsResult)
    }
}