package com.sourav1.marvel.Model.DataClasses.ComicsData

data class Characters(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)