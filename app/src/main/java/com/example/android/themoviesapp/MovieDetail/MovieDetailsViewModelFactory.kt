package com.example.android.themoviesapp.MovieDetail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.MoviesListing.MoviesListingViewModel
import com.example.android.themoviesapp.Others.ApiPreferences

class MovieDetailsViewModelFactory(private var bundle:Bundle?,private val dataSource: MoviesDatabase, private val apiPreferences: ApiPreferences):
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(bundle,dataSource,apiPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}