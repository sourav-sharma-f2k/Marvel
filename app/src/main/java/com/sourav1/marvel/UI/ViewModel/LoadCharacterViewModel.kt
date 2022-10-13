package com.sourav1.marvel.UI.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sourav1.marvel.Model.Data.CharacterData.Result
import com.sourav1.marvel.Repository.Repository
import com.sourav1.marvel.Util.Constants.Companion.API_KEY
import com.sourav1.marvel.Util.Constants.Companion.HASH
import com.sourav1.marvel.Util.Constants.Companion.timeStamp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class LoadCharacterViewModel : ViewModel() {
    val result: MutableLiveData<List<Result>> = MutableLiveData()
    private lateinit var repo: Repository

    init {
        repo = Repository()
        getResultList()
    }

    private fun getResultList() = viewModelScope.launch {
        val response = repo.getAllCharactersFromApi(API_KEY, timeStamp, HASH)
        result.postValue(response.data.results)
    }
}