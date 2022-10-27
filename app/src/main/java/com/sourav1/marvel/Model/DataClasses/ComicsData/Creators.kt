package com.sourav1.marvel.Model.DataClasses.ComicsData

data class Creators(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemX>,
    val returned: Int
)