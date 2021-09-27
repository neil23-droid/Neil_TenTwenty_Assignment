package com.example.android.themoviesapp.BookTicket

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.MoviesListing.MoviesListingViewModel
import com.example.android.themoviesapp.Others.ApiPreferences

class BookTicketViewModelFactory(private var bundle:Bundle?, private val dataSource: MoviesDatabase, private val apiPreferences: ApiPreferences):
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(BookTicketViewModel::class.java)) {
            return BookTicketViewModel(bundle,dataSource,apiPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}