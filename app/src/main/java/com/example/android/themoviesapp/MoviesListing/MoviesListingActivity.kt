package com.example.android.themoviesapp.MoviesListing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.themoviesapp.Adapters.UpComingMoviesAdapter
import com.example.android.themoviesapp.BookedTicketHistory.BookedTicketHistoryActivity
import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.MovieDetail.MovieDetailsActivity
import com.example.android.themoviesapp.Others.ApiPreferences
import com.example.android.themoviesapp.R
import com.example.android.themoviesapp.databinding.ActivityMoviesListingBinding

class MoviesListingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesListingBinding
    private lateinit var viewModel: MoviesListingViewModel
    private lateinit var upComingMoviesAdapter: UpComingMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoviesListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeToolbar()

        initializeViewModel()

        initializeUpComingMoviesRV()

        initializeObservers()


        callGetMoviesScript()



    }

    private fun initializeToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

    }


    /*
    * Assign ViewModel to activity*/
    private fun initializeViewModel(){

        val application = requireNotNull(this).application
        val dataSource = MoviesDatabase.getInstance(application)
        val apiPreferences = ApiPreferences(application)
        val viewModelFactory = MoviesListingViewModelFactory(dataSource,apiPreferences)

        viewModel = ViewModelProvider(
                this, viewModelFactory).get(MoviesListingViewModel::class.java)
    }

    /*
    *
    * initialize upcoming movies recycler view*/
    private fun initializeUpComingMoviesRV(){

        binding.upComingMoviesRV.layoutManager = LinearLayoutManager(this)
        upComingMoviesAdapter = UpComingMoviesAdapter(this,{

            val i = Intent(this@MoviesListingActivity,MovieDetailsActivity::class.java)
            i.putExtra("MOVIE_ID",it.movieId)
            i.putExtra("MOVIE_NAME",it.originalTitle)
            startActivity(i)
        })
        binding.upComingMoviesRV.adapter = upComingMoviesAdapter
    }

    private fun initializeObservers(){
/*
*
* observer to check for up coming movies list*/
        viewModel.upComingMoviesList.observe(this,{

            if(it.isEmpty()){
                binding.materialPorgressBar.visibility = View.GONE
                Toast.makeText(this@MoviesListingActivity,"No Data Available",Toast.LENGTH_SHORT).show()
            }else {
                binding.materialPorgressBar.visibility = View.GONE
                upComingMoviesAdapter.updateList(it)
            }
        })
    }

    private fun callGetMoviesScript(){
        binding.materialPorgressBar.visibility = View.VISIBLE
        viewModel.callUpComingMoviesScript()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_options_menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){

            R.id.bookedTicket->{
                val i = Intent(this@MoviesListingActivity,BookedTicketHistoryActivity::class.java)
                startActivity(i)

                return true
            }
            else->{
                super.onOptionsItemSelected(item)
            }
        }
    }
}