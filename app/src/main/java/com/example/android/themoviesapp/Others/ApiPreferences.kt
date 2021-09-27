package com.example.android.themoviesapp.Others

import android.content.Context
import android.content.SharedPreferences
import com.example.android.themoviesapp.Database.MoviesDatabase

private const val API_PREFERENCES = "API_PREFERENCES"
private const val CALL_UP_COMING_MOVIES_API= "CALL_UP_COMING_MOVIES_API"

class ApiPreferences (context: Context){


    private val prefs:SharedPreferences = context.getSharedPreferences(API_PREFERENCES,Context.MODE_PRIVATE)

    var callUpComingMoviesAPI:Boolean?
        get() = prefs.getBoolean(CALL_UP_COMING_MOVIES_API,false)
        set(value) = prefs.edit().putBoolean(CALL_UP_COMING_MOVIES_API,value?:false).apply()
}