package com.vitali.tikaltestapp

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies", indices = [Index(value = ["id"], unique = true)])
data class Movie(
    @PrimaryKey(autoGenerate = true) val index: Long,
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("vote_count") val voteCount: Int = 0,
    @field:SerializedName("video") val video: Boolean = false,
    @field:SerializedName("vote_average") val voteAverage: Double = 0.0,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("popularity") val popularity: Double = 0.0,
    @field:SerializedName("poster_path") val posterPath: String?,
    @field:SerializedName("backdrop_path") val backdropPath: String?,
    @field:SerializedName("overview") val overview: String,
    @field:SerializedName("release_date") val releaseDate: String?,
    var page: Int
)



