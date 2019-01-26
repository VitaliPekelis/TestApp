package com.vitali.tikaltestapp

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.vitali.scanovatetest.Logger

class MoviesRepository(
    private val service: MoviesService,
    private val cache: MoviesCache)
{

    fun requestMoviesOnFirstTime(): MoviesResult
    {
        Logger.logDebug(logText = "New Query Movies")

        val dataSourceFactory = cache.getAll()

        val boundaryCallback = RepoBoundaryCallback(service,cache)
        val networkError = boundaryCallback.networkErrors

        val config = PagedList.Config.Builder()
            .setPageSize(DATA_PAGE_SIZE)
            //.setInitialLoadSizeHint(50) //default page size * 3
            //.setPrefetchDistance(20)//default: page size
            //.setEnablePlaceholders(true)//default: true
            .build()


        val data = LivePagedListBuilder(dataSourceFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()

        return MoviesResult(data, networkError)
    }



    companion object {

        private const val DATA_PAGE_SIZE = 20
    }

}