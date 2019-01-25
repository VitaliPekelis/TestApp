package com.vitali.tikaltestapp

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MoviesAdapter : ListAdapter<Movie, RecyclerView.ViewHolder> (MOVIE_COMPARATOR)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        return MovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
    {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as MovieViewHolder).bind(repoItem)
        }
    }

    companion object
    {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>()
        {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

}
