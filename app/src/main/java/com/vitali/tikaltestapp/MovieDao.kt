package com.vitali.tikaltestapp


import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(list:List<Movie>)

    @Query("SELECT * FROM movies ORDER BY `index` ASC")
    fun movies(): DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM movies ORDER BY `index` DESC LIMIT 1")
    fun lastMovie(): Movie

}
