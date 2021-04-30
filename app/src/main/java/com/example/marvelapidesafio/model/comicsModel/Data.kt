package com.example.marvelapidesafio.model.comicsModel


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("limit")
    val limit: Int?,
    @SerializedName("offset")
    val offset: Int?,
    @SerializedName("results")
    val comicsList: List<ComicsList>?,
    @SerializedName("total")
    val total: Int?
)