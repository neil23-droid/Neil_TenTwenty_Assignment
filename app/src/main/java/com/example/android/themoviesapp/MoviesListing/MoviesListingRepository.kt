package com.example.android.themoviesapp.MoviesListing

import androidx.lifecycle.LiveData
import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.Database.UpcomingMoviesTable

import com.example.android.themoviesapp.Others.ApiPreferences
import com.example.android.themoviesapp.Others.asDatabaseModel
import com.example.android.themoviesapp.Others.asNetworkModel
import com.example.android.themoviesapp.Retrofit.MoviesDBClient
import com.example.android.themoviesapp.Retrofit.ResponseMessageDetails
import com.example.android.themoviesapp.Retrofit.UpComingMovies

import retrofit2.HttpException
import java.lang.Exception

class MoviesListingRepository(private val database: MoviesDatabase,private val apiPreferences: ApiPreferences) {



    suspend fun getUpcomingMoviesAPI(): ResponseMessageDetails{
        var messageDetails = ResponseMessageDetails()
        try {
            val response = MoviesDBClient.api.getUpComingMovies()

            if(response.isSuccessful && response.body()!=null){

                /*
                * Collect the movies list from the API response*/
                val upComingMoviesResponse = response.body()!!
                var upComingMoviesList = upComingMoviesResponse.moviesList

                val ids = database.moviesDao.insertAll(upComingMoviesList.asDatabaseModel())

                if(ids.isNotEmpty()){
                    /*
                    * if successfully inserted into DB*/

                    messageDetails = ResponseMessageDetails("Successful Inserted Data In DB",
                        response.code().toString()?:"",
                    true)

                }else{
                    /*if unsuccessful
                    * */
                    messageDetails = ResponseMessageDetails("Error in inserting data",
                        response.code().toString()?:"",
                        false)
                }






            }
        }
        catch (e:HttpException){
            /*
            * check if HttpException and populate the error message*/

            messageDetails = ResponseMessageDetails("${e.code()}",
                "${e.message()}",
                false)
        }
        catch (e:Exception){

            messageDetails = ResponseMessageDetails("${0}",
                "${e.localizedMessage}",
                false)
        }

        return messageDetails
    }


    suspend fun getUpComingMoviesFromDB(): ArrayList<UpComingMovies>{

        val upComingMoviesList = database.moviesDao.getUpComingMoviesList() as ArrayList
        return upComingMoviesList.asNetworkModel()
    }
}