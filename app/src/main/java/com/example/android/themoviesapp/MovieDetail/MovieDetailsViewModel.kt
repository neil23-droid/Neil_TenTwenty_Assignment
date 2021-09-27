package com.example.android.themoviesapp.MovieDetail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.MoviesListing.MoviesListingRepository
import com.example.android.themoviesapp.Others.ApiPreferences
import com.example.android.themoviesapp.Retrofit.GetMovieDetailsResponse
import com.example.android.themoviesapp.Retrofit.GetMoviePostersResponse
import com.example.android.themoviesapp.Retrofit.GetMovieTrailerUrlsResponse
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.lang.Exception

class MovieDetailsViewModel(private var bundle: Bundle?, private val dataSource: MoviesDatabase, private val apiPreferences: ApiPreferences):ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope =  CoroutineScope(Dispatchers.Main + viewModelJob)
    private var repository: MovieDetailsRepository = MovieDetailsRepository(dataSource,apiPreferences)

    private var movieId:Long = 0

    //movie details
    private var _movieDetails:MutableLiveData<GetMovieDetailsResponse> = MutableLiveData()
    val movieDetails :LiveData<GetMovieDetailsResponse>
        get() = _movieDetails
    private var movieDetailsBundle:GetMovieDetailsResponse?=null

    //movie posters
    private var _moviePosters:MutableLiveData<GetMoviePostersResponse> = MutableLiveData()
    val moviePosters:LiveData<GetMoviePostersResponse>
        get() = _moviePosters

    //movie urls
    private var _movieUrls:MutableLiveData<GetMovieTrailerUrlsResponse> = MutableLiveData()
    val movieUrls:LiveData<GetMovieTrailerUrlsResponse>
        get() = _movieUrls


    private var _bundleData:MutableLiveData<Bundle> = MutableLiveData()
    val bundleData:LiveData<Bundle>
        get() = _bundleData


    init {
        movieId= bundle?.getLong("MOVIE_ID",0)?:0
    }

    /*
    * script call for movie details*/
    fun callMovieDetailsScript(){

        var getMovieDetailsResponse:GetMovieDetailsResponse?=null
        var httpException:HttpException?=null
        var exception:Exception?=null
        var otherMessage:String?=null
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                val response = repository.getMovieDetailsAPI(movieId)

                when(response){

                    is GetMovieDetailsResponse->{
                        getMovieDetailsResponse = response
                    }

                    is HttpException ->{
                        httpException = response as HttpException
                    }

                    is Exception->{
                        exception = response as Exception
                    }
                    else->{

                    }
                }
            }


            if(getMovieDetailsResponse!=null) {
                /*
            * update the bundle incase user wants to go to next screen*/
                movieDetailsBundle = getMovieDetailsResponse

                /*
            * notify UI through observer*/
                _movieDetails.postValue(getMovieDetailsResponse)
            }


        }
    }

    /*
    *
    * for getting the movie posters*/
    fun callMoviePostersScript(){
        var getMoviePostersResponse:GetMoviePostersResponse?=null
        var httpException:HttpException?=null
        var exception:Exception?=null
        var otherMessage:String?=null
        viewModelScope.launch {

            withContext(Dispatchers.IO){

                val response = repository.getMoviePosters(movieId)

                when(response){

                    is GetMoviePostersResponse->{
                        getMoviePostersResponse = response
                    }
                    is HttpException->{
                        httpException = response
                    }
                    is Exception ->{
                        exception = response
                    }
                    else->{

                    }
                }

            }

            if(getMoviePostersResponse?.posters?.isNotEmpty()?:false){
                _moviePosters.postValue(getMoviePostersResponse)
            }
        }
    }


    /*
    * for getting trailer
    * */
    fun callMovieTrailerScript(){
        var getMovieTrailersResponse:GetMovieTrailerUrlsResponse?=null
        var httpException:HttpException?=null
        var exception:Exception?=null
        var otherMessage:String?=null
        viewModelScope.launch {

            withContext(Dispatchers.IO){

                val response = repository.getMovieTrailers(movieId)

                when(response){

                    is GetMovieTrailerUrlsResponse->{
                        getMovieTrailersResponse = response
                    }
                    is HttpException->{
                        httpException = response
                    }
                    is Exception ->{
                        exception = response
                    }
                    else->{

                    }
                }

            }

            if(getMovieTrailersResponse?.urlResults?.isNotEmpty()?:false){
                _movieUrls.postValue(getMovieTrailersResponse)
            }
        }
    }


    fun navigateToBookTicketScreen(){

        var bundle:Bundle = Bundle()
        bundle.putParcelable("MOVIE_DETAILS",movieDetailsBundle)

        _bundleData.postValue(bundle)

    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}