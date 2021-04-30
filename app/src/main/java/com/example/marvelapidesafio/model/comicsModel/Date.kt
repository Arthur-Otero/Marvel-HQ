package com.example.marvelapidesafio.model.comicsModel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Date(
    @SerializedName("date")
    val date: String?,
    @SerializedName("type")
    val type: String?
) : Serializable