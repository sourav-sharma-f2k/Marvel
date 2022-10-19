package com.sourav1.marvel.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sourav1.marvel.Database.Entities.CharacterResult
import com.sourav1.marvel.Repository.Repository
import com.sourav1.marvel.Util.Constants.Companion.API_KEY
import com.sourav1.marvel.Util.Constants.Companion.HASH
import com.sourav1.marvel.Util.Constants.Companion.timeStamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
@HiltViewModel
class LoadCharacterViewModel @Inject constructor(private val repo: Repository) : ViewModel() {
    val result: MutableLiveData<List<CharacterResult>> = MutableLiveData()
    val resultRecommended: MutableLiveData<List<CharacterResult>> = MutableLiveData()

    init {
        refreshCharacters()
    }


    private fun refreshCharacters() = viewModelScope.launch {
        val request = GlobalScope.async {
            repo.refreshCharacters(API_KEY, timeStamp, HASH)
        }
        request.join()
        getCharactersResult()
    }

    private suspend fun getCharactersResult() {
        withContext(Dispatchers.IO) {
            result.postValue(repo.getCharactersResult())
        }
    }

    fun refreshRecommendedCharacters() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            resultRecommended.postValue(repo.getRecommendedCharactersList())
        }
    }

    fun increaseClick(id: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            repo.increaseClickCount(id)
        }
    }

}