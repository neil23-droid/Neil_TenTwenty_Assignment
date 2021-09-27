package com.example.android.themoviesapp.BookTicket

import com.example.android.themoviesapp.Database.BookedTicketHistoryTable
import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.Others.ApiPreferences

class BookTicketRepository(private val database: MoviesDatabase, private val apiPreferences: ApiPreferences) {


    suspend fun insertTicket(data:BookedTicketHistoryTable):Long{

        val id = database.bookedTicketHistoryDao.insertTicket(data)

        return id

    }
}