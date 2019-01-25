package com.vitali.tikaltestapp

import androidx.lifecycle.MutableLiveData
import com.vitali.scanovatetest.Logger


class MoviesRepo(
    private val service : MoviesService,
    private val cache : MoviesCache)
{

    private val liveDataNetworkErrors = MutableLiveData<String>()

    private var isRequestInProgress = false
    private var lastRequestedPage = 1
    private var totalPage = 1


    fun requestMovies(): MoviesResult
    {
        Logger.logDebug(logText = "New Query Movies")
        lastRequestedPage = 1
        totalPage = 1
        fetchAndSaveMovies()

        val liveDataMovies = cache.getAll()

        return MoviesResult(liveDataMovies, liveDataNetworkErrors)
    }


    fun requestMoreMovies()
    {
        fetchAndSaveMovies()
    }

    private fun fetchAndSaveMovies()
    {
        if (isRequestInProgress || lastRequestedPage > totalPage) return

        isRequestInProgress = true

        fetchMovies(service, lastRequestedPage,
            { movies, countPages ->
                cache.insert(movies)
                {
                    totalPage = countPages
                    lastRequestedPage++
                    isRequestInProgress = false
                }

            },
            { error ->
                isRequestInProgress = false
                liveDataNetworkErrors.postValue(error)
            })

    }

}