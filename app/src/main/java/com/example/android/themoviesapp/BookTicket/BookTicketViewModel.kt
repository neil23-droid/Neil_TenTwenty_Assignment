package com.example.android.themoviesapp.BookTicket

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.themoviesapp.DataModels.CinemaData
import com.example.android.themoviesapp.DataModels.LocationData
import com.example.android.themoviesapp.DataModels.SeatData
import com.example.android.themoviesapp.DataModels.ValidationMessages
import com.example.android.themoviesapp.Database.BookedTicketHistoryTable
import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.MovieDetail.MovieDetailsRepository
import com.example.android.themoviesapp.Others.ApiPreferences
import com.example.android.themoviesapp.Retrofit.GetMovieDetailsResponse
import kotlinx.coroutines.*

class BookTicketViewModel(private var bundle: Bundle?, private val dataSource: MoviesDatabase, private val apiPreferences: ApiPreferences): ViewModel() {


    private val viewModelJob = Job()
    private val viewModelScope =  CoroutineScope(Dispatchers.Main + viewModelJob)
    private var repository: BookTicketRepository = BookTicketRepository(dataSource,apiPreferences)

    //for validating input
    private var _areValidationsSatisfied:MutableLiveData<ValidationMessages> = MutableLiveData()
    val areValidationsSatisfied:LiveData<ValidationMessages>
        get() = _areValidationsSatisfied

    private var movieDetails:GetMovieDetailsResponse?=null

    private var _navigateToMainScreen:MutableLiveData<Boolean> = MutableLiveData()
    val navigateToMainScreen:LiveData<Boolean>
        get() = _navigateToMainScreen


    init {
        movieDetails = bundle?.getParcelable("MOVIE_DETAILS")
    }



    fun validateSelections(selectedLocation:LocationData, selectedCinemaData: CinemaData, selectedSeat:SeatData):String{

        if(selectedLocation.id.equals("0")){

            val validationMessages = ValidationMessages("Please select a location",false)
            _areValidationsSatisfied.postValue(validationMessages)
            return ""
        }

        if(selectedCinemaData.id.equals("0")){
            val validationMessages = ValidationMessages("Please select a Cinema",false)
            _areValidationsSatisfied.postValue(validationMessages)
            return ""
        }

        if(selectedSeat.id.equals("0")){
            val validationMessages = ValidationMessages("Please select a seat number",false)
            _areValidationsSatisfied.postValue(validationMessages)
            return ""
        }

        _areValidationsSatisfied.postValue(ValidationMessages("",true))
        return ""
    }

    fun insertTicketIntoDB(selectedLocation:LocationData,
                           selectedCinemaData: CinemaData,
                           selectedSeat:SeatData){
        viewModelScope.launch {
            var id :Long = 0L
            withContext(Dispatchers.IO){

                var ticketDetails = BookedTicketHistoryTable(
                    movieDetails?.adult?:false,
                    movieDetails?.backdropPath?:"",
                    movieDetails?.originalLanguage?:"",
                    movieDetails?.originalTitle?:"",
                    movieDetails?.overview?:"",
                    movieDetails?.popularity?:0.0,
                    movieDetails?.posterPath?:"",
                    movieDetails?.releaseDate?:"",
                    movieDetails?.title?:"",
                    selectedLocation?.place?:"",
                    selectedCinemaData?.cinemaName?:"",
                    selectedSeat.SeatNumber?:""

                )

                id = repository.insertTicket(ticketDetails)


            }


            if(id > 0){
                //inserted ticket details successfully
                _navigateToMainScreen.postValue(true)

            }else{
                _navigateToMainScreen.postValue(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}