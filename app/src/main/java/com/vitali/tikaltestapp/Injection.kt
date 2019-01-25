package com.vitali.tikaltestapp

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import java.util.concurrent.Executors

object Injection {

    private fun provideDb(context: Context): MoviesCache {
        val database = MoviesDb.getInstance(context)
        return MoviesCache(database.reposDao(), Executors.newSingleThreadExecutor())
    }


    private fun provideMoviesRepo(context: Context): MoviesRepo {
        return MoviesRepo(MoviesService.create(), provideDb(context))
    }


    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideMoviesRepo(context))
    }
}