package com.vitali.tikaltestapp

import com.google.gson.annotations.SerializedName

data class MoviesResponse (
    @SerializedName("results") val items: List<Movie> = emptyList(),
    @SerializedName("page") val page:Int = 1,
    @SerializedName("total_pages") val totalPages:Int = 1
)
