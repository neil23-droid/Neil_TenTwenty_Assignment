package com.example.android.themoviesapp.MoviesListing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.themoviesapp.Database.MoviesDao
import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.Others.ApiPreferences

class MoviesListingViewModelFactory( private val dataSource: MoviesDatabase,private val apiPreferences: ApiPreferences): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MoviesListingViewModel::class.java)) {
            return MoviesListingViewModel(dataSource,apiPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}