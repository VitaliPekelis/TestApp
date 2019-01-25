package com.vitali.tikaltestapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
abstract class MoviesDb: RoomDatabase() {


    abstract fun reposDao(): MovieDao

    companion object {

        @Volatile
        private var INSTANCE: MoviesDb? = null

        fun getInstance(context: Context): MoviesDb =
             INSTANCE ?: synchronized(this){
                 INSTANCE ?: buildDataBase(context).also { INSTANCE = it }
             }

        private fun buildDataBase(context: Context): MoviesDb =
            Room.databaseBuilder(context.applicationContext,MoviesDb::class.java,"movies.db")
                .build()


    }
}