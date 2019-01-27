package com.vitali.tikaltestapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.vitali.scanovatetest.Logger

class RepoBoundaryCallback(
    private val service:MoviesService,
    private val cache: MoviesCache) : PagedList.BoundaryCallback<Movie>()
{

    private var isRequestInProgress = false
    private var lastRequestedPage = 1
    //private var totalPage = 1

    private val liveDataNetworkErrors = MutableLiveData<String>()

    val networkErrors:LiveData<String>
    get() = liveDataNetworkErrors


    override fun onZeroItemsLoaded() {
        Logger.logDebug()
        fetchAndSaveMovies()
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        Logger.logDebug("onItemAtEndLoaded",logText = itemAtEnd.title)
        lastRequestedPage = itemAtEnd.page
        lastRequestedPage++
        fetchAndSaveMovies()
    }

    override fun onItemAtFrontLoaded(itemAtFront: Movie) {
        super.onItemAtFrontLoaded(itemAtFront)
        Logger.logDebug("onItemAtFrontLoaded",logText = itemAtFront.title)
    }


    private fun fetchAndSaveMovies()
    {
        if (isRequestInProgress) return

        isRequestInProgress = true

        Logger.logDebug(logText = "lastRequestedPage $lastRequestedPage")

        fetchMovies(service, lastRequestedPage,
            { movies ->
                cache.insert(movies)
                {
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