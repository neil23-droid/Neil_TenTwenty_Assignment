package com.example.android.themoviesapp.Database

import android.content.Context
import androidx.room.*

@Database(entities = [UpcomingMoviesTable::class, BookedTicketHistoryTable::class], version = 2,  exportSchema = false)
@TypeConverters(LongConverter::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val moviesDao: MoviesDao
    abstract val bookedTicketHistoryDao: BookedTicketHistoryDao

    companion object {

        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, MoviesDatabase::class.java, "movies_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance

            }
        }
    }


}