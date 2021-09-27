package com.example.android.themoviesapp.Database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.android.themoviesapp.Retrofit.UpComingMovies

const val TABLE_NAME_UPCOMING_MOVIES = "UPCOMING_MOVIES_TABLE"
const val TABLE_NAME_BOOKED_TICKET_HISTORY = "BOOKED_TICKET_HISTORY_TABLE"


@Entity(tableName = TABLE_NAME_UPCOMING_MOVIES, indices = arrayOf(Index(value = ["movieId"], unique = true)) )
data class UpcomingMoviesTable(
   var adult:Boolean = false,
   var backdropPath: String? = null,
   @TypeConverters(LongConverter::class) var genreIds: ArrayList<Long> = arrayListOf(),
   var movieId: Long = 0,
   var originalLanguage: String? = null,
   var originalTitle: String? = null,
   var overview: String? = null,
   var popularity:Double = 0.0,
   var posterPath: String? = null,
   var releaseDate: String? = null,
   var title: String? = null,
   var video:Boolean = false,
   var voteAverage:Double = 0.0,
   var voteCount: Long = 0
){
    @PrimaryKey(autoGenerate = true )var _id:Long = 0
}


@Entity(tableName = TABLE_NAME_BOOKED_TICKET_HISTORY)
data class BookedTicketHistoryTable(
   var adult:Boolean = false,
   var backdropPath: String? = null,
   var originalLanguage: String? = null,
   var originalTitle: String? = null,
   var overview: String? = null,
   var popularity:Double = 0.0,
   var posterPath: String? = null,
   var releaseDate: String? = null,
   var title: String? = null,
   var selectedLocation:String?=null,
   var selectedCinema:String?=null,
   var selectedSeat:String?=null
){
   @PrimaryKey(autoGenerate = true )var _id:Long = 0
}


