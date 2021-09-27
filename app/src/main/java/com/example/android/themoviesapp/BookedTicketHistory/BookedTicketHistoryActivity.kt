package com.example.android.themoviesapp.BookedTicketHistory

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.themoviesapp.Adapters.BookedTicketHistoryAdapter
import com.example.android.themoviesapp.BookTicket.BookTicketViewModel
import com.example.android.themoviesapp.BookTicket.BookTicketViewModelFactory
import com.example.android.themoviesapp.Database.BookedTicketHistoryTable
import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.Others.ApiPreferences
import com.example.android.themoviesapp.R
import com.example.android.themoviesapp.databinding.ActivityBookedTicketHistoryBinding

class BookedTicketHistoryActivity : AppCompatActivity() {

    private lateinit var binding:ActivityBookedTicketHistoryBinding

    private lateinit var bookedTicketHistoryAdapter:BookedTicketHistoryAdapter

    private var bookedTicketsList:ArrayList<BookedTicketHistoryTable> = arrayListOf()

    private lateinit var viewModel: BookedTicketHistoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookedTicketHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeToolbar()

        initializeRV()

        initializeViewModel()

        initializeObservers()

        getBookedTicketsList()
    }


    private fun initializeToolbar(){
        setSupportActionBar(binding.toolbar)
        binding.navIcon.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initializeRV(){
        binding.bookedTicketsRV.layoutManager = LinearLayoutManager(this)
        bookedTicketHistoryAdapter = BookedTicketHistoryAdapter(this,{})
        binding.bookedTicketsRV.adapter = bookedTicketHistoryAdapter
    }

    private fun initializeViewModel(){

        val application = requireNotNull(this).application
        val dataSource = MoviesDatabase.getInstance(application)
        val apiPreferences = ApiPreferences(application)
        val viewModelFactory = BookedTicketHistoryViewModelFactory(dataSource,apiPreferences)

        viewModel = ViewModelProvider(
            this, viewModelFactory).get(BookedTicketHistoryViewModel::class.java)
    }

    private fun initializeObservers(){

        viewModel.bookedTicketList.observe(this,{

            if(it.isEmpty()){
                Toast.makeText(this,"You have not booked any tickets",Toast.LENGTH_SHORT).show()
            }else{
                //notify the adapter and display list in reverse order
                    val reversedBookedTicketsList = it.sortedByDescending {it._id  }
                bookedTicketHistoryAdapter.updateList(reversedBookedTicketsList)
            }
        })
    }

    private fun getBookedTicketsList(){

        viewModel.getBookedTicketsList()
    }
}