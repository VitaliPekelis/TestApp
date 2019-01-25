package com.vitali.tikaltestapp

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import java.util.concurrent.Executors

object Injection {

    private fun provideDb(context: Context): MoviesCache {
        val database = MoviesDb.getInstance(context)
        return MoviesCache(database.reposDao(), Executors.newSingleThreadExecutor())
    }


    private fun provideMoviesRepo(context: Context): MoviesRepository {
        return MoviesRepository(MoviesService.create(), provideDb(context), provideSharedPref(context))
    }

    private fun provideSharedPref(context: Context): SharedPrefHelper {
        return SharedPrefHelper.create(context)
    }


    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(provideMoviesRepo(context))
    }
}