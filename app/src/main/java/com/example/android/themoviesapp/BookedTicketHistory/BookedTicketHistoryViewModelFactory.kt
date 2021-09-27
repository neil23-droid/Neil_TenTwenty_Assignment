package com.example.android.themoviesapp.BookedTicketHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.MovieDetail.MovieDetailsViewModel
import com.example.android.themoviesapp.Others.ApiPreferences

class BookedTicketHistoryViewModelFactory(private val database: MoviesDatabase, private val apiPreferences: ApiPreferences) :
    ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(BookedTicketHistoryViewModel::class.java)) {
            return BookedTicketHistoryViewModel(database,apiPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}