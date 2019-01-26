package com.vitali.tikaltestapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

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
            //val resources = itemView.resources
            //name.text = resources.getString(R.string.loading)

            /*description.visibility = View.GONE
            language.visibility = View.GONE
            stars.text = resources.getString(R.string.unknown)
            forks.text = resources.getString(R.string.unknown)*/
        }
        else
        {
            showRepoData(movie)
        }
    }

    private fun showRepoData(item: Movie)
    {
        this.movie = item

        titleV.text = item.title
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
