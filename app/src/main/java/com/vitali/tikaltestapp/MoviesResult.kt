package com.vitali.tikaltestapp

import androidx.lifecycle.LiveData

data class MoviesResult(
    val data: LiveData<List<Movie>>,
    val networkErrors: LiveData<String>
)
