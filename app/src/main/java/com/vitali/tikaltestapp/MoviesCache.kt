package com.vitali.tikaltestapp

import androidx.paging.DataSource
import com.vitali.scanovatetest.Logger
import java.util.concurrent.Executor


class MoviesCache (
    private val moveDao: MovieDao,
    private val ioExecutor:Executor)
{

    fun insert(movies: List<Movie>, insertFinished: () -> Unit) {
        ioExecutor.execute {
            Logger.logDebug("MoviesCache", "inserting ${movies.size} movies")
            moveDao.insert(movies)
            insertFinished()
        }
    }

    fun getAll(): /*LiveData<PagedList<Movie>>*/DataSource.Factory<Int, Movie>
    {
        return moveDao.movies()
    }


}
