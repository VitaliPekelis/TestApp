package com.vitali.tikaltestapp

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class MoviesResult(
    val data: LiveData<PagedList<Movie>>,
    val networkErrors: LiveData<String>
)
