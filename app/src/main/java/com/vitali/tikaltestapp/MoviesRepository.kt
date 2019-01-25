package com.vitali.tikaltestapp

import androidx.lifecycle.MutableLiveData
import com.vitali.scanovatetest.Logger


private const val KEY_PAGE = "KEY_PAGE"

class MoviesRepository(
    private val service: MoviesService,
    private val cache: MoviesCache,
    private val sharedPrefHelper: SharedPrefHelper)
{

    private val liveDataNetworkErrors = MutableLiveData<String>()

    private var isRequestInProgress = false
    private var lastRequestedPage = 1
    private var totalPage = 1


    fun requestMoviesOnFirstTime(): MoviesResult
    {
        Logger.logDebug(logText = "New Query Movies")
        totalPage = 1
        lastRequestedPage = 1

        // fetch first page on start
        fetchAndSaveMovies()

        val liveDataMovies = cache.getAll()
        lastRequestedPage = sharedPrefHelper.getSharedPrefInt(KEY_PAGE, defValue = 1)

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

        Logger.logDebug(logText = "lastRequestedPage $lastRequestedPage")

        fetchMovies(service, lastRequestedPage,
            { movies, countPages,page ->
                cache.insert(movies)
                {
                    totalPage = countPages
                    lastRequestedPage++
                    sharedPrefHelper.setSharedPref(KEY_PAGE, page, false)
                    isRequestInProgress = false
                }

            },
            { error ->
                isRequestInProgress = false
                liveDataNetworkErrors.postValue(error)
            })

    }

}