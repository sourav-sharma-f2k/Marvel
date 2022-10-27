package com.sourav1.marvel.Presentation.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sourav1.marvel.Data.Database.Entities.CharacterResult
import com.sourav1.marvel.Domain.Repository.Repository
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

    val recommendedClicked: MutableLiveData<Boolean> = MutableLiveData()
    val recommendedButtonText: MutableLiveData<String> = MutableLiveData()

    init {
        refreshCharacters()
        recommendedClicked.postValue(false)
        recommendedButtonText.postValue("Show Recommended Characters")
    }


    private fun refreshCharacters() = viewModelScope.launch {
        val cache = repo.getCharactersResult()
        if(cache.isNotEmpty()){
            withContext(Dispatchers.IO){
                result.postValue(cache)
            }
        }

        val request = GlobalScope.async {
            repo.refreshCharacters(API_KEY, timeStamp, HASH)
        }
        request.join()
        getCharactersResult()
    }

    suspend fun getCharactersResult() {
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