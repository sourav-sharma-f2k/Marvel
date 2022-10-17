package com.sourav1.marvel.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sourav1.marvel.Database.Entities.CharacterResult
import com.sourav1.marvel.Database.Entities.ComicsResult

@Database(entities = [CharacterResult::class, ComicsResult::class], version = 1)
abstract class DbInstance : RoomDatabase() {
    abstract fun dao(): Dao

    companion object {
        var INSTANCE: DbInstance? = null

        fun getInstance(context: Context): DbInstance {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DbInstance::class.java,
                        "marvelDb"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}