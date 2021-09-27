package com.example.android.themoviesapp.BookedTicketHistory

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.themoviesapp.BookTicket.BookTicketRepository
import com.example.android.themoviesapp.Database.BookedTicketHistoryTable
import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.Database.UpcomingMoviesTable
import com.example.android.themoviesapp.Others.ApiPreferences
import kotlinx.coroutines.*

class BookedTicketHistoryViewModel(private val dataSource: MoviesDatabase, private val apiPreferences: ApiPreferences):ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope =  CoroutineScope(Dispatchers.Main + viewModelJob)
    private var repository: BookedTicketHistoryRepository = BookedTicketHistoryRepository(dataSource,apiPreferences)



    private var _bookedTicketList:MutableLiveData<ArrayList<BookedTicketHistoryTable>> = MutableLiveData()
    val bookedTicketList:LiveData<ArrayList<BookedTicketHistoryTable>>
        get() = _bookedTicketList




    fun getBookedTicketsList(){
        var bookedTicketsList:ArrayList<BookedTicketHistoryTable> = arrayListOf()
        viewModelScope.launch {
            withContext(Dispatchers.IO){

                bookedTicketsList = repository.getBookedTicketsFromDB()
            }

            _bookedTicketList.postValue(bookedTicketsList)
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}