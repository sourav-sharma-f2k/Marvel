package com.sourav1.marvel.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sourav1.marvel.Database.Entities.CharacterResult
import com.sourav1.marvel.Database.Entities.ComicsResult
import com.sourav1.marvel.Model.Data.CharacterData.Result

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharactersResult(result: List<CharacterResult>)

    @Query("SELECT * FROM characterResult ORDER BY id ASC")
    fun getCharacterResult(): List<CharacterResult>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComicsResult(result: List<ComicsResult>)

    @Query("SELECT * FROM comicsResult WHERE parentId = :id ORDER BY id ASC")
    fun getComicsResult(id: Int): List<ComicsResult>
}