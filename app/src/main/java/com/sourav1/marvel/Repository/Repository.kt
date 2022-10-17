package com.sourav1.marvel.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.sourav1.marvel.Database.DbInstance
import com.sourav1.marvel.Database.Entities.CharacterResult
import com.sourav1.marvel.Database.Entities.ComicsResult
import com.sourav1.marvel.Retrofit.MarvelApi
import com.sourav1.marvel.Retrofit.RetrofitInstance
import com.sourav1.marvel.Util.Constants.Companion.API_KEY
import com.sourav1.marvel.Util.Constants.Companion.HASH
import com.sourav1.marvel.Util.Constants.Companion.parseCharacterResponseToCharacterResult
import com.sourav1.marvel.Util.Constants.Companion.parseComicResponseToComicsResult
import com.sourav1.marvel.Util.Constants.Companion.timeStamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val context: Context) {
    private val marvelApiInstance: MarvelApi =
        RetrofitInstance.getInstance().create(MarvelApi::class.java)
    private val dbInstance: DbInstance = DbInstance.getInstance(context)

    suspend fun refreshCharacters(apiKey: String, ts: String, hash: String) {
        withContext(Dispatchers.IO) {
            val response = marvelApiInstance.getCharacters(API_KEY, timeStamp, HASH)
            val mResponse: List<CharacterResult>

            withContext(Dispatchers.Main) {
                mResponse = parseCharacterResponseToCharacterResult(response)
            }

            dbInstance.dao()
                .insertCharactersResult(mResponse)
        }
    }

    suspend fun refreshComics(url: String, apiKey: String, ts: String, hash: String, parentId: Int) {
        withContext(Dispatchers.IO) {
            val response = marvelApiInstance.getComics(url, apiKey, ts, hash)
            val mResponse: List<ComicsResult>

            withContext(Dispatchers.Main) {
                mResponse = parseComicResponseToComicsResult(response, parentId)
            }

            dbInstance.dao().insertComicsResult(mResponse)
        }
    }

    suspend fun getCharactersResult(): List<CharacterResult> {
        return dbInstance.dao().getCharacterResult()
    }

    suspend fun getComicsResult(id: Int): List<ComicsResult> {
        return dbInstance.dao().getComicsResult(id)
    }
}