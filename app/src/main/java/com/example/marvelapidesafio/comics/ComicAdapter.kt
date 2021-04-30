package com.example.marvelapidesafio.comics

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapidesafio.R
import com.example.marvelapidesafio.comics.details.ComicDetailsActivity
import com.example.marvelapidesafio.comics.details.ComincsInfos
import com.example.marvelapidesafio.model.comicsModel.ComicsList
import com.squareup.picasso.Picasso
import java.lang.StringBuilder

class ComicAdapter() : RecyclerView.Adapter<ComicAdapter.ComicViewHolder>() {
    private var comics = mutableListOf<ComicsList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.banner_catalog, parent, false)
        return ComicViewHolder(view)
    }

    override fun getItemCount() = comics.size

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val imagePath = comics[position]

        Picasso.get().load(imagePath.thumbnail?.path + "." + imagePath.thumbnail?.extension)
            .into(holder.image)
        holder.card.setOnClickListener {
            val intent = Intent(it.context, ComicDetailsActivity::class.java)
            ComincsInfos.comicsInfo = imagePath
            it.context.startActivity(intent)
        }

//        holder.edition.text = "# ${position+1}"

        val sb = StringBuilder()
        if (imagePath.title?.contains('#') == true) {
            imagePath.title.lastIndex.let {
                sb.appendRange(
                    imagePath.title, imagePath.title.indexOf('#'),
                    it+1
                )
            }
            holder.edition.text = sb
        }else holder.edition.text = "#..."
    }

    inner class ComicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView by lazy { view.findViewById<ImageView>(R.id.comicImage) }
        val card: CardView by lazy { view.findViewById<CardView>(R.id.card) }
        val edition: TextView by lazy { view.findViewById<TextView>(R.id.edition) }
    }

    fun addComic(listComics: List<ComicsList>) {
        comics.addAll(listComics)
        notifyDataSetChanged()
    }
}