package com.sourav1.marvel.Data.Database.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characterResult")
data class CharacterResult(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String?,
    val thumbnailUrl: String,
    val thumbnailExtension: String,
    val comicsListURI: String,
    val clickCount: Int = 0
)
