package com.vitali.tikaltestapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list:List<Movie>)

    @Query("SELECT * FROM movies ORDER BY `index` ASC")
    fun movies(): LiveData<List<Movie>>

}
