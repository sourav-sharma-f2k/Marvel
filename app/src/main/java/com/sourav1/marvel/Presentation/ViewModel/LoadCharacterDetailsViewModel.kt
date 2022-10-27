package com.sourav1.marvel.Presentation.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sourav1.marvel.Data.Database.Entities.ComicsResult
import com.sourav1.marvel.Domain.Repository.Repository
import com.sourav1.marvel.Util.Constants.Companion.API_KEY
import com.sourav1.marvel.Util.Constants.Companion.HASH
import com.sourav1.marvel.Util.Constants.Companion.timeStamp
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class LoadCharacterDetailsViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    val result: MutableLiveData<List<ComicsResult>> = MutableLiveData()

    @OptIn(DelicateCoroutinesApi::class)
    fun refreshComics(url: String, apiKey: String, timeStamp: String, hash: String, id: Int) = viewModelScope.launch {
        val request = GlobalScope.async {
            repo.refreshComics(url, apiKey, timeStamp, hash, id)
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