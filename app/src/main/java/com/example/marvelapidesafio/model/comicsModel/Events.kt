package com.example.marvelapidesafio.model.comicsModel


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Events(
    @SerializedName("available")
    val available: Int?,
    @SerializedName("collectionURI")
    val collectionURI: String?,
    @SerializedName("items")
    val items: List<ItemXX>?,
    @SerializedName("returned")
    val returned: Int?
): Serializable