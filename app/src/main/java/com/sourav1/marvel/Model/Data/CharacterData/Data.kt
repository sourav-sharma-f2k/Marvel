package com.sourav1.marvel.Model.Data.CharacterData

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Result>,
    val total: Int
)