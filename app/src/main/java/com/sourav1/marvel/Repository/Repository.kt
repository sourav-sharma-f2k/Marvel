package com.sourav1.marvel.Repository

import com.sourav1.marvel.Model.Data.CharacterData.Characters
import com.sourav1.marvel.Model.Data.ComicsData.Comics
import com.sourav1.marvel.Retrofit.MarvelApi
import com.sourav1.marvel.Retrofit.RetrofitInstance

class Repository {
    val marvelApiInstance: MarvelApi = RetrofitInstance.getInstance().create(MarvelApi::class.java)

    suspend fun getAllCharactersFromApi(apiKey: String, ts: String, hash: String): Characters {
        return marvelApiInstance.getCharacters(apiKey, ts, hash).body()!!
    }

    suspend fun getComics(url: String, apiKey: String, ts: String, hash: String): Comics {
        return marvelApiInstance.getComics(url, apiKey, ts, hash).body()!!
    }
}