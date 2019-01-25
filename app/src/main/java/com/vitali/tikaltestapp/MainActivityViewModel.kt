package com.vitali.tikaltestapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

private const val VISIBLE_THRESHOLD = 8

class MainActivityViewModel(private val repository: MoviesRepo): ViewModel()
{

    private var moviesResult  = MutableLiveData<MoviesResult>()

    val movies: LiveData<List<Movie>> = Transformations.switchMap(moviesResult) {
        it.data
    }
    val networkErrors: LiveData<String> = Transformations.switchMap(moviesResult) { it ->
        it.networkErrors
    }



    fun getMovies()
    {
        val result = repository.requestMovies()
        moviesResult.postValue(result)
    }



    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int)
    {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount)
        {
            repository.requestMoreMovies()
        }
    }

}