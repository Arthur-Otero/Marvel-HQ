package com.example.marvelapidesafio.comics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapidesafio.R
import com.google.android.material.snackbar.Snackbar

class ComicActivity : AppCompatActivity() {
    private val recycle by lazy { findViewById<RecyclerView>(R.id.recycleComic) }
    private val first by lazy { findViewById<ProgressBar>(R.id.firstRequisition)}
    private val paging by lazy { findViewById<ProgressBar>(R.id.paging) }

    private val recycleScrollListener by lazy { RecycleScrollListener { viewModel.comicsNextPage() } }

    lateinit var viewModel: ComicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic)

        viewModel = ViewModelProviders.of(this).get(ComicViewModel::class.java)

        val adapter = ComicAdapter()
        recycle.adapter = adapter
        recycle.layoutManager = GridLayoutManager(this, 3)
        recycle.addOnScrollListener(recycleScrollListener)

        viewModel.comicLiveData.observe(this) {
            setRequestingNextPage()
            adapter.addComic(it)
        }

        viewModel.firstPageLoading.observe(this){
            if (it){
                first.visibility = VISIBLE
            }else
                first.visibility = GONE
        }

        viewModel.nextPageLoading.observe(this){
            if (it){
                paging.visibility = VISIBLE
            }else
                paging.visibility = GONE
        }

        viewModel.errorMessage.observe(this){
            Snackbar.make(recycle,it,Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setRequestingNextPage() {
        recycleScrollListener.requesting = false
    }
}