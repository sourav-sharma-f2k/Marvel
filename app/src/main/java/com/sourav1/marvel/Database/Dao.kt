package com.sourav1.marvel.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sourav1.marvel.Database.Entities.CharacterResult
import com.sourav1.marvel.Database.Entities.ComicsResult
import com.sourav1.marvel.Model.Data.CharacterData.Result

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharactersResult(result: List<CharacterResult>)

    @Query("SELECT * FROM characterResult ORDER BY id ASC")
    fun getCharacterResult(): List<CharacterResult>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertComicsResult(result: List<ComicsResult>)

    @Query("SELECT * FROM comicsResult WHERE parentId = :id ORDER BY id ASC")
    fun getComicsResult(id: Int): List<ComicsResult>

    @Query("UPDATE characterResult SET clickCount = clickCount + 1 WHERE id = :id")
    fun increaseClickCount(id: Int)

    @Query("SELECT * FROM characterResult WHERE clickCount >= 1 ORDER BY clickCount DESC")
    fun getRecommendedCharactersList(): List<CharacterResult>
}