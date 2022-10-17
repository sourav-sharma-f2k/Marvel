package com.sourav1.marvel.UI.ViewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sourav1.marvel.Database.Entities.ComicsResult
import com.sourav1.marvel.Repository.Repository
import com.sourav1.marvel.Util.Constants.Companion.API_KEY
import com.sourav1.marvel.Util.Constants.Companion.HASH
import com.sourav1.marvel.Util.Constants.Companion.timeStamp
import kotlinx.coroutines.*

class LoadCharacterDetailsViewModel(val url: String, context: Context, val id: Int) : ViewModel() {
    val result: MutableLiveData<List<ComicsResult>> =
        MutableLiveData()
    private lateinit var repo: Repository

    init {
        repo = Repository(context)
        refreshComics()
    }

    private fun refreshComics() = viewModelScope.launch {
        val request = GlobalScope.async {
            repo.refreshComics(url, API_KEY, timeStamp, HASH, id)
        }
        request.join()
        getComicsResult(id)
    }

    private suspend fun getComicsResult(id: Int) {
        withContext(Dispatchers.IO) {
            result.postValue(repo.getComicsResult(id))
        }
    }
}