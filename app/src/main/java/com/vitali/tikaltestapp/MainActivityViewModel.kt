package com.vitali.tikaltestapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList

class MainActivityViewModel(private val repository: MoviesRepository): ViewModel()
{

    private var moviesResult  = MutableLiveData<MoviesResult>()

    val movies: LiveData<PagedList<Movie>> = Transformations.switchMap(moviesResult) {
        it.data
    }
    val networkErrors: LiveData<String> = Transformations.switchMap(moviesResult) { it ->
        it.networkErrors
    }

    val clickedMovie = MutableLiveData<Movie>()



    fun getMovies()
    {
        val result = repository.requestMoviesOnFirstTime()
        moviesResult.postValue(result)
    }

}