package com.example.android.themoviesapp.BookedTicketHistory

import com.example.android.themoviesapp.Database.BookedTicketHistoryTable
import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.Others.ApiPreferences

class BookedTicketHistoryRepository(private val database: MoviesDatabase, private val apiPreferences: ApiPreferences) {


    suspend fun getBookedTicketsFromDB():ArrayList<BookedTicketHistoryTable>{


        val bookedTicketsList =database.bookedTicketHistoryDao.getBookedTicketHistoryList()
        if(bookedTicketsList.isEmpty()){
            return arrayListOf()
        }else{
            return bookedTicketsList as ArrayList<BookedTicketHistoryTable>
        }
    }



}