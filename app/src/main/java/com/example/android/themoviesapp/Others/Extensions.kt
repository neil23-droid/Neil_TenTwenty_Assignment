package com.example.android.themoviesapp.Others

import com.example.android.themoviesapp.Database.UpcomingMoviesTable
import com.example.android.themoviesapp.Retrofit.UpComingMovies
import java.text.SimpleDateFormat
import java.util.*


/*
* taking date in milliseconds and converting it to dd MMM yyyy*/
fun String.getDate():String{


    return try{
        val formatter = SimpleDateFormat("dd MMM yyyy")
        val dateString = formatter.format(this.toLong())

        return dateString

    }catch (e:Exception){
        ""
    }
}

fun String.getDate2():String{

    return try {
        val originalFormat =SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val targetFormat = SimpleDateFormat("dd MMM yyyy")
        val date = originalFormat.parse(this)
        val formattedDate = targetFormat.format(date)
        return formattedDate
    }catch (e:java.lang.Exception){
        ""
    }

}

/**
 * Convert Network results to Table Objects
 */

fun ArrayList<UpComingMovies>.asDatabaseModel(): ArrayList<UpcomingMoviesTable> {

    var upComingMovies:ArrayList<UpcomingMoviesTable> = arrayListOf()

    this.forEach{

        upComingMovies.add(
            UpcomingMoviesTable(
                it.adult?:false,
                it?.backdropPath?:"",
                it?.genreIds?: arrayListOf(),
                it?.movieId?:0L,
                it?.originalLanguage?:"",
                it?.originalTitle?:"",
                it?.overview?:"",
                it?.popularity?:0.0,
                it?.posterPath?:"",
                it?.releaseDate?:"",
                it?.originalTitle?:"",
                it?.video?:false,
                it?.voteAverage?:0.0,
                it?.voteCount?:0L
            )
        )
    }

    return upComingMovies
}


/*
*
* convert table objects to Network objects*/

fun ArrayList<UpcomingMoviesTable>.asNetworkModel(): ArrayList<UpComingMovies> {

    var upComingMovies:ArrayList<UpComingMovies> = arrayListOf()

    this.forEach{

        upComingMovies.add(
            UpComingMovies(
                it.adult?:false,
                it?.backdropPath?:"",
                it?.genreIds?: arrayListOf(),
                it?.movieId?:0L,
                it?.originalLanguage?:"",
                it?.originalTitle?:"",
                it?.overview?:"",
                it?.popularity?:0.0,
                it?.posterPath?:"",
                it?.releaseDate?:"",
                it?.originalTitle?:"",
                it?.video?:false,
                it?.voteAverage?:0.0,
                it?.voteCount?:0L
            )
        )
    }

    return upComingMovies
}

