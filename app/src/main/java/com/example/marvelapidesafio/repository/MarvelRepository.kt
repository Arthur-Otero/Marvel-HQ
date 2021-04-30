package com.example.marvelapidesafio.repository

import com.example.marvelapidesafio.MD5
import com.example.marvelapidesafio.model.comicsModel.Comics
import com.example.marvelapidesafio.network.RetrofitMarvel
import com.example.marvelapidesafio.network.ServiceAPI

class MarvelRepository {

    private var ts: String? = java.lang.Long.toString(System.currentTimeMillis() / 1000)
    private val PUBLIC_KEY = "bea228eef47a3911b60477a6645c8731"
    private val PRIVATE_KEY = "d944953884938a4f4e8aff826c2c719baec337cc"
    private var hash = MD5.md5(ts + PRIVATE_KEY + PUBLIC_KEY)


    private var url = "https://gateway.marvel.com/v1/public/"
    private var service = ServiceAPI::class

    private val serviceRepository = RetrofitMarvel(url).create(service)

    suspend fun getComics(offset: Int = 0, orderBy: String = "-focDate"): Comics =
        serviceRepository.getComics(offset, orderBy, ts, hash, PUBLIC_KEY)
}