package com.example.marvelapidesafio.model.comicsModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicsList(
    @SerializedName("characters")
    val characters: Characters?,
    @SerializedName("collectedIssues")
    val collectedIssues: List<CollectedIssue>?,
    @SerializedName("collections")
    val collections: List<Collection>?,
    @SerializedName("creators")
    val creators: Creators?,
    @SerializedName("dates")
    val dates: List<Date>?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("diamondCode")
    val diamondCode: String?,
    @SerializedName("digitalId")
    val digitalId: Int?,
    @SerializedName("ean")
    val ean: String?,
    @SerializedName("events")
    val events: Events?,
    @SerializedName("format")
    val format: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("images")
    val images: List<Image>?,
    @SerializedName("isbn")
    val isbn: String?,
    @SerializedName("issn")
    val issn: String?,
    @SerializedName("issueNumber")
    val issueNumber: Int?,
    @SerializedName("modified")
    val modified: String?,
    @SerializedName("pageCount")
    val pageCount: Int?,
    @SerializedName("prices")
    val prices: List<Price>?,
    @SerializedName("resourceURI")
    val resourceURI: String?,
    @SerializedName("series")
    val series: Series?,
    @SerializedName("stories")
    val stories: Stories?,
    @SerializedName("textObjects")
    val textObjects: List<TextObject>?,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("upc")
    val upc: String?,
    @SerializedName("urls")
    val urls: List<Url>?,
    @SerializedName("variantDescription")
    val variantDescription: String?,
    @SerializedName("variants")
    val variants: List<Any>?
): Serializable