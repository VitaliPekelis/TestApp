package com.vitali.tikaltestapp

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


    fun fetchMovies(service: MoviesService,
                    page:Int,
                    onSuccess: (movies: List<Movie>) -> Unit,
                    onError:(error:String) -> Unit)
    {
        service.fetchMovies(page).enqueue(object:Callback<MoviesResponse>{
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable)
            {
                onError(t.message ?: "Unknown error")
            }

            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>)
            {
                if(response.isSuccessful)
                {
                    val body = response.body()
                    body?.let {
                        it.setupPage()
                        onSuccess(it.items)
                    }
                }
                else
                {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }

            }

        })
    }

interface MoviesService {

    @GET("/3/movie/popular?api_key=9219429a11e390b7e41d687342cfda09&language=en-US")
    fun fetchMovies(@Query("page") page:Int): Call<MoviesResponse>

    companion object {
        fun create() : MoviesService {
            val clientBuilder = OkHttpClient.Builder()

            if(BuildConfig.DEBUG){
                clientBuilder.addNetworkInterceptor(StethoInterceptor())
            }

            return Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientBuilder.build())
                .build()
                .create(MoviesService::class.java)
        }
    }
}