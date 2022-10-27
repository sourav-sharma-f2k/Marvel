package com.sourav1.marvel.Data.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sourav1.marvel.Data.Database.Entities.CharacterResult
import com.sourav1.marvel.Data.Database.Entities.ComicsResult

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharactersResult(result: List<CharacterResult>)

    @Query("SELECT * FROM characterResult ORDER BY id ASC")
    suspend fun getCharacterResult(): List<CharacterResult>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertComicsResult(result: List<ComicsResult>)

    @Query("SELECT * FROM comicsResult WHERE parentId = :id ORDER BY id ASC")
    suspend fun getComicsResult(id: Int): List<ComicsResult>

    @Query("UPDATE characterResult SET clickCount = clickCount + 1 WHERE id = :id")
    suspend fun increaseClickCount(id: Int)

    @Query("SELECT * FROM characterResult WHERE clickCount >= 1 ORDER BY clickCount DESC")
    suspend fun getRecommendedCharactersList(): List<CharacterResult>
}