package com.example.marvelapidesafio.model.comicsModel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Price(
    @SerializedName("price")
    val price: Double?,
    @SerializedName("type")
    val type: String?
): Serializable