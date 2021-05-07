package com.example.marvelapidesafio.comics.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.marvelapidesafio.R
import com.squareup.picasso.Picasso

class ComicDetailsActivity : AppCompatActivity() {
    private val backdrop by lazy { findViewById<ImageView>(R.id.backdrop) }
    private val banner by lazy { findViewById<ImageView>(R.id.comicImage)}
    private val title by lazy { findViewById<TextView>(R.id.title) }
    private val description by lazy { findViewById<TextView>(R.id.description) }
    private val button by lazy { findViewById<ImageView>(R.id.backButton)}
    private val page by lazy { findViewById<TextView>(R.id.page) }
    private val price by lazy { findViewById<TextView>(R.id.price) }
    private val date by lazy { findViewById<TextView>(R.id.date) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_details)

        val comics = ComicsInfo.comicsInfo

        Picasso.get().load(comics?.thumbnail?.path +"."+comics?.thumbnail?.extension).into(banner)
        Picasso.get().load(comics?.thumbnail?.path +"."+comics?.thumbnail?.extension).into(backdrop)
        title.text = comics?.series?.name
        if (!comics?.description.isNullOrBlank())
            description.text = comics?.description

        page.text = "Pages: ${comics?.pageCount}"
        price.text = "Price: $ ${comics?.prices?.get(0)?.price}"
        comics?.dates?.forEach {
            if (it.type.equals("focDate")) {
                date.text = "Date: " + it.date?.removeRange(9, it.date.lastIndex).toString()
            }
        }

        button.setOnClickListener{
            onBackPressed()
        }

        banner.setOnClickListener {
            val intent = Intent(this,ImageActivity::class.java)
            startActivity(intent)
        }
    }
}