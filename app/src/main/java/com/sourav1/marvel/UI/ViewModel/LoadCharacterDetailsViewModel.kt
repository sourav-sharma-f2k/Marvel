package com.sourav1.marvel.UI.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sourav1.marvel.Model.Data.ComicsData.Result
import com.sourav1.marvel.Repository.Repository
import com.sourav1.marvel.Util.Constants.Companion.API_KEY
import com.sourav1.marvel.Util.Constants.Companion.HASH
import com.sourav1.marvel.Util.Constants.Companion.timeStamp
import kotlinx.coroutines.launch

class LoadCharacterDetailsViewModel(val url: String) : ViewModel() {
    val comicsResult: MutableLiveData<List<Result>> = MutableLiveData()
    lateinit var repo: Repository

    init {
        repo = Repository()
        getComicResult()
    }

    private fun getComicResult() = viewModelScope.launch {
        val response = repo.getComics(url, API_KEY, timeStamp, HASH)
        comicsResult.postValue(response.data.results)
    }
}