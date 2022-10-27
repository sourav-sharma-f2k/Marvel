package com.sourav1.marvel.Model.DataClasses.CharacterData

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)