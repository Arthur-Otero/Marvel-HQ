package com.example.marvelapidesafio.comics

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvelapidesafio.model.comicsModel.Comics
import com.example.marvelapidesafio.model.comicsModel.ComicsList
import com.example.marvelapidesafio.repository.MarvelRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

class ComicViewModel : ViewModel() {
    val comicLiveData by lazy { MutableLiveData<List<ComicsList>>() }
    val nextPageLoading by lazy { MutableLiveData<Boolean>() }
    val firstPageLoading by lazy { MutableLiveData<Boolean>() }
    val errorMessage = MutableLiveData<String>()

    private val repository = MarvelRepository()

    var nextPage = 0

    init {
        comicsList()
    }

    private fun comicsList() = CoroutineScope(IO).launch {
        try{
            firstPageLoading.postValue(true)
            repository.getComics().let {
                updateNextPage(it)
                comicLiveData.postValue(it.data?.comicsList)
            }
        }catch(error:Throwable){
            safeApi(error,errorMessage)
        }finally {
            firstPageLoading.postValue(false)
        }
    }

    private fun updateNextPage(comic:Comics){
        Log.d("offset", comic.data?.offset.toString())
        nextPage = comic.data?.offset?.plus(20) ?:0
    }

    fun comicsNextPage() = CoroutineScope(IO).launch {
        try{
            nextPageLoading.postValue(true)
            repository.getComics(offset = nextPage).let {
                updateNextPage(it)
                comicLiveData.postValue(it.data?.comicsList)
            }
        }catch(error:Throwable){
            safeApi(error,errorMessage)
        }finally {
            nextPageLoading.postValue(false)
        }
    }
}

fun safeApi(error: Throwable, errorMessage: MutableLiveData<String>) {
    when (error) {
        is HttpException -> errorMessage.postValue("Erro de conexão código: ${error.code()}")
        is UnknownHostException -> errorMessage.postValue("Verifique sua conexão")
    }
}