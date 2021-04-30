package com.example.marvelapidesafio.model.comicsModel


import com.google.gson.annotations.SerializedName

data class ItemX(
    @SerializedName("name")
    val name: String?,
    @SerializedName("resourceURI")
    val resourceURI: String?,
    @SerializedName("role")
    val role: String?
)