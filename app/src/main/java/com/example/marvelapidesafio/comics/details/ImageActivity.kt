package com.example.marvelapidesafio.comics.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.marvelapidesafio.R
import com.squareup.picasso.Picasso

class ImageActivity : AppCompatActivity() {
    private val cancel by lazy { findViewById<ImageView>(R.id.cancel) }
    private val image by lazy { findViewById<ImageView>(R.id.image) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        cancel.setOnClickListener {
            onBackPressed()
        }

        val comics = ComincsInfos.comicsInfo
        Picasso.get().load(comics?.thumbnail?.path +"."+comics?.thumbnail?.extension).into(image)
    }
}