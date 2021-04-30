package com.example.marvelapidesafio.comics

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecycleScrollListener(val onRequestMore: () -> Unit): RecyclerView.OnScrollListener() {
    var requesting = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val target = recyclerView.layoutManager as GridLayoutManager
        val totalItemCount = target.itemCount
        val lastVisible = target.findLastVisibleItemPosition()
        val lastItem = lastVisible + 8 >= totalItemCount
        if (totalItemCount > 0 && lastItem && !requesting){
            onRequestMore()
            setRequestingNextPage(true)
        }
    }

    fun setRequestingNextPage(requesting: Boolean){
        this.requesting = requesting
    }
}