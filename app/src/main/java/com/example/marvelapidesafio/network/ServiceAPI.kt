package com.example.marvelapidesafio.network

import com.example.marvelapidesafio.model.comicsModel.Comics
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceAPI {

    @GET("comics?")
    suspend fun getComics(
        @Query("offset") offset:Int?,
        @Query("orderBy") orderBy:String?,
        @Query("ts") ts:String?,
        @Query("hash") hash:String?,
        @Query("apikey") apiKey: String
    ) : Comics
}