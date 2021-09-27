package com.example.android.themoviesapp.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(videos: ArrayList<UpcomingMoviesTable>):List<Long>

    @Query("Select * FROM UPCOMING_MOVIES_TABLE")
    suspend fun getUpComingMoviesList():List<UpcomingMoviesTable>
}

@Dao
interface BookedTicketHistoryDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket:BookedTicketHistoryTable):Long

    @Query("Select * FROM BOOKED_TICKET_HISTORY_TABLE")
    suspend fun getBookedTicketHistoryList():List<BookedTicketHistoryTable>

}