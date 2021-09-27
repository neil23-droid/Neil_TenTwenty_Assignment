package com.example.android.themoviesapp.MovieDetail

import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.Others.ApiPreferences
import com.example.android.themoviesapp.Others.DEFAULT_ERROR_MESSAGE
import com.example.android.themoviesapp.Retrofit.MoviesDBClient
import retrofit2.HttpException
import java.lang.Exception

class MovieDetailsRepository(private val database: MoviesDatabase, private val apiPreferences: ApiPreferences) {


    suspend fun getMovieDetailsAPI(movieId:Long):Any{

        try {

            val response = MoviesDBClient.api.getMovieDetails(movieId)
            val getMovieDetails = response.body()

            if(response.isSuccessful && getMovieDetails!=null){


                return getMovieDetails

            }

        }catch (e:HttpException){

            return e

        }catch (e:Exception){

            return e
        }

        return DEFAULT_ERROR_MESSAGE

    }


    suspend fun getMoviePosters(movieId: Long):Any{
        try {

            val response = MoviesDBClient.api.getMovieImages(movieId)
            val moviePostersResponse = response.body()!!

            if(response.isSuccessful && response.body()!=null){

                return moviePostersResponse
            }
        }catch (e:HttpException){
            return e
        }catch (e:Exception){
            return e
        }

        return DEFAULT_ERROR_MESSAGE
    }



    suspend fun getMovieTrailers(movieId: Long):Any{
        try {

            val response = MoviesDBClient.api.getMovieTrailerUrl(movieId)
            val movieTrailersResponse = response.body()!!

            if(response.isSuccessful && response.body()!=null){

                return movieTrailersResponse
            }
        }catch (e:HttpException){
            return e
        }catch (e:Exception){
            return e
        }

        return DEFAULT_ERROR_MESSAGE
    }
}