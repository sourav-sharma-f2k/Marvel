package com.sourav1.marvel.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sourav1.marvel.Database.Entities.CharacterResult
import com.sourav1.marvel.Model.Data.CharacterData.Result
import com.sourav1.marvel.Repository.Repository
import com.sourav1.marvel.Util.Constants.Companion.API_KEY
import com.sourav1.marvel.Util.Constants.Companion.HASH
import com.sourav1.marvel.Util.Constants.Companion.timeStamp
import kotlinx.coroutines.*
import java.lang.Exception

@OptIn(DelicateCoroutinesApi::class)
class LoadCharacterViewModel(context: Context) : ViewModel() {
    val result: MutableLiveData<List<CharacterResult>> = MutableLiveData()
    val resultRecommended: MutableLiveData<List<CharacterResult>> = MutableLiveData()

    private lateinit var repo: Repository

    init {
        repo = Repository(context)
        refreshCharacters()
    }

    private fun refreshCharacters() = viewModelScope.launch {
       val request= GlobalScope.async {
            repo.refreshCharacters(API_KEY, timeStamp, HASH)
        }
        request.join()
        getCharactersResult()
    }

    private suspend fun getCharactersResult(){
        withContext(Dispatchers.IO){
            result.postValue(repo.getCharactersResult())
        }
    }

     fun refreshRecommendedCharacters() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            resultRecommended.postValue(repo.getRecommendedCharactersList())
        }
    }
}