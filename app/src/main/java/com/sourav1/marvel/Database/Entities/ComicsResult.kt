package com.sourav1.marvel.Database.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comicsResult")
data class ComicsResult(
    @PrimaryKey val id: Int,
    val parentId: Int,
    val title: String,
    val description: String?,
    val thumbnailUrl: String,
    val thumbnailExtension: String,
    val printPrice: Double,
    val digitalPurchasePrice: Double,
    val pageCount: Int,
    val detailsUrl: String?,
    val purchaseUrl: String?
)