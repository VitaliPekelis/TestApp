package com.vitali.tikaltestapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vitali.scanovatetest.Logger

class MovieViewHolder(view: View, onMovieClick:(Movie) -> Unit) :RecyclerView.ViewHolder(view)
{
    private val imageV: ImageView = view.findViewById(R.id.movie_item_image_iv)
    private val titleV: TextView = view.findViewById(R.id.movie_item_title)

    private var movie: Movie? = null

    init {
        view.setOnClickListener {
            movie?.let{
                onMovieClick(it)
            }
        }
    }

    fun bind(movie: Movie?)
    {
        if (movie == null)
        {
            Logger.logDebug(logText = "movie == null")
            val resources = itemView.resources
            titleV.visibility = View.VISIBLE
            titleV.text = resources.getString(R.string.loading)
        }
        else
        {
            showRepoData(movie)
        }
    }

    private fun showRepoData(item: Movie)
    {
        this.movie = item

        titleV.visibility = View.GONE

        Picasso.get().load("https://image.tmdb.org/t/p/w300${item.posterPath}").into(imageV)
    }

    companion object {
        fun create(parent: ViewGroup, onMovieClick:(Movie) -> Unit): MovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_view_item, parent, false)
            return MovieViewHolder(view, onMovieClick)
        }
    }
}
