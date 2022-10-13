package com.sourav1.marvel.Retrofit

import com.sourav1.marvel.Model.Data.CharacterData.Characters
import com.sourav1.marvel.Model.Data.ComicsData.Comics
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface MarvelApi {
    @GET("/v1/public/characters")
     suspend fun getCharacters(
        @Query("apikey")apikey:String,
        @Query("ts")ts:String,
        @Query("hash")hash:String,
        @Query("limit")limit:Int = 100
        ): Response<Characters>

     @GET
     suspend fun getComics(
         @Url()url: String,
         @Query("apikey")apikey:String,
         @Query("ts")ts:String,
         @Query("hash")hash:String,
     ): Response<Comics>
}