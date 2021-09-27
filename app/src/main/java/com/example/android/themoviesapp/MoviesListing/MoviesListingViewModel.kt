package com.example.android.themoviesapp.MoviesListing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.themoviesapp.Database.MoviesDao
import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.Database.UpcomingMoviesTable
import com.example.android.themoviesapp.Others.ApiPreferences
import com.example.android.themoviesapp.Retrofit.ResponseMessageDetails
import com.example.android.themoviesapp.Retrofit.UpComingMovies
import kotlinx.coroutines.*

class MoviesListingViewModel(private val database: MoviesDatabase,private val apiPreferences: ApiPreferences) :ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope =  CoroutineScope(Dispatchers.Main + viewModelJob)
    private var repository:MoviesListingRepository = MoviesListingRepository(database,apiPreferences)

    private lateinit var messageDetails: ResponseMessageDetails

    private var _upComingMoviesList: MutableLiveData<ArrayList<UpComingMovies>> = MutableLiveData()
    val upComingMoviesList: LiveData<ArrayList<UpComingMovies>>
        get() = _upComingMoviesList

    init {



    }


    fun callUpComingMoviesScript(){

        var moviesList:ArrayList<UpComingMovies> = arrayListOf()

        if(apiPreferences.callUpComingMoviesAPI?:false) {
            /*
            * If true , then API call was already done and database has data
            * */

            viewModelScope.launch {
                withContext(Dispatchers.IO){
                    moviesList = repository.getUpComingMoviesFromDB()
                }
                if (moviesList.isEmpty()) {
                    _upComingMoviesList.postValue(arrayListOf())
                } else {
                    _upComingMoviesList.postValue(moviesList)
                }
            }

        }else{

            viewModelScope.launch {
                withContext(Dispatchers.IO) {

                    messageDetails = repository.getUpcomingMoviesAPI()

                    if (messageDetails.isSuccessFul) {
                        /*
                    * if insertion into DB was successful,
                    * set preferences to call API just once to true*/
                        moviesList = repository.getUpComingMoviesFromDB()
                        apiPreferences.callUpComingMoviesAPI = true
                    } else {

                    }
                }

                if (moviesList.isEmpty()) {
                    _upComingMoviesList.postValue(arrayListOf())
                } else {
                    _upComingMoviesList.postValue(moviesList)
                }


            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}