package com.sourav1.marvel.Data.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sourav1.marvel.Data.Database.Entities.CharacterResult
import com.sourav1.marvel.Data.Database.Entities.ComicsResult

@Database(entities = [CharacterResult::class, ComicsResult::class], version = 1)
abstract class DbInstance : RoomDatabase() {
    abstract fun dao(): Dao
}