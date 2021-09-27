package com.example.android.themoviesapp.BookTicket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.android.themoviesapp.Adapters.CinemaSpinnerAdapter
import com.example.android.themoviesapp.Adapters.LocationSpinnerAdapter
import com.example.android.themoviesapp.Adapters.SeatSpinnerAdapter
import com.example.android.themoviesapp.DataModels.CinemaData
import com.example.android.themoviesapp.DataModels.LocationData
import com.example.android.themoviesapp.DataModels.SeatData
import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.MovieDetail.MovieDetailsViewModel
import com.example.android.themoviesapp.MovieDetail.MovieDetailsViewModelFactory
import com.example.android.themoviesapp.MoviesListing.MoviesListingActivity
import com.example.android.themoviesapp.Others.ApiPreferences
import com.example.android.themoviesapp.Others.DEFAULT_ERROR_MESSAGE
import com.example.android.themoviesapp.R
import com.example.android.themoviesapp.databinding.ActivityBookTicketBinding

class BookTicketActivity : AppCompatActivity() {

    private lateinit var binding:ActivityBookTicketBinding
    private lateinit var viewModel: BookTicketViewModel

    //location spinner
    private lateinit var locationSpinnerAdapter: LocationSpinnerAdapter
    private var locationsList:ArrayList<LocationData> = arrayListOf(
        LocationData("0","Select Location"),
        LocationData("1","Margao"),
        LocationData("2","Panjim"),
        LocationData("3","Porvorim"),
        LocationData("4","Vasco Da Gama")
    )
    private lateinit var selectedLocation: LocationData

    //cinema spinner
    private lateinit var cinemaSpinnerAdapter: CinemaSpinnerAdapter
    private var cinemaList:ArrayList<CinemaData> = arrayListOf(
        CinemaData("0","Select Cinema"),
        CinemaData("1","Inox, KTC, Margo"),
        CinemaData("2","Inox, Old GMC, Panjim"),
        CinemaData("3","Inox, Mall de Goa, Porvorim"),
        CinemaData("4","PVR, 1933, Vasco")
    )
    private lateinit var selectedCinema: CinemaData

    //seat spinner
    private lateinit var seatSpinnerAdapter: SeatSpinnerAdapter
    private var seatList:ArrayList<SeatData> = arrayListOf(
        SeatData("0","Select Seat"),
        SeatData("1","1A"),
        SeatData("2","2A"),
        SeatData("3","3A"),
        SeatData("4","4A"),
        SeatData("5","5A"),
    )
    private lateinit var selectedSeat:SeatData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeToolbar()

        initLocationSpinner()

        initCinemaSpinner()

        initSeatCinemaSpinner()

        initializeViewModel()

        initializeOberservers()

        initOnClickListeners()




    }

    private fun initializeToolbar(){
        setSupportActionBar(binding.toolbar)
        binding.navIcon.setOnClickListener {
            onBackPressed()
        }
    }

    private fun  initLocationSpinner(){

        locationSpinnerAdapter = LocationSpinnerAdapter(
            this,
            R.layout.spinner_item,
            locationsList
        )
        locationSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.locationSpinner.adapter = locationSpinnerAdapter

        locationSpinnerAdapter.updateList(locationsList)

        binding.locationSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedLocation = binding.locationSpinner.getItemAtPosition(position) as LocationData
            }

        }

    }

    private fun  initCinemaSpinner(){

        cinemaSpinnerAdapter = CinemaSpinnerAdapter(
            this,
            R.layout.spinner_item,
            cinemaList
        )
        cinemaSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.cinemaSpinner.adapter = cinemaSpinnerAdapter

        cinemaSpinnerAdapter.updateList(cinemaList)

        binding.cinemaSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCinema = binding.cinemaSpinner.getItemAtPosition(position) as CinemaData
            }

        }

    }

    private fun initSeatCinemaSpinner(){
        seatSpinnerAdapter = SeatSpinnerAdapter(
            this,
            R.layout.spinner_item,
            seatList
        )
        seatSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.seatSpinner.adapter = seatSpinnerAdapter

        seatSpinnerAdapter.updateList(seatList)

        binding.seatSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedSeat = binding.seatSpinner.getItemAtPosition(position) as SeatData
            }

        }
    }

    private fun initOnClickListeners(){
        binding.confirmTicketCL.setOnClickListener {

            validateSelections()
        }
    }


    /*
* Assign ViewModel to activity*/
    private fun initializeViewModel(){

        val application = requireNotNull(this).application
        val bundle = intent?.extras
        val dataSource = MoviesDatabase.getInstance(application)
        val apiPreferences = ApiPreferences(application)
        val viewModelFactory = BookTicketViewModelFactory(bundle,dataSource,apiPreferences)

        viewModel = ViewModelProvider(
            this, viewModelFactory).get(BookTicketViewModel::class.java)
    }

    private fun initializeOberservers(){

        //validate user input
        viewModel.areValidationsSatisfied.observe(this,{

            if(it.isSatisfied){

                //if validations are satisfied, proceed to save ticket information

                viewModel.insertTicketIntoDB(
                    selectedLocation,
                    selectedCinema,
                    selectedSeat
                )


            }else{
                Toast.makeText(this@BookTicketActivity,
                    it.message,
                    Toast.LENGTH_LONG).show()
            }
        })


        viewModel.navigateToMainScreen.observe(this,{

            if(it){
                Toast.makeText(this@BookTicketActivity,
                    "Your ticket details have been confirmed. Kindly check your ticket under Booked Tickets",
                    Toast.LENGTH_LONG).show()
                val i = Intent(this@BookTicketActivity,MoviesListingActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(i)
            }else{
                Toast.makeText(this@BookTicketActivity, DEFAULT_ERROR_MESSAGE,Toast.LENGTH_LONG).show()
            }
        })

    }


    //validate user in put
    private fun validateSelections(){
        if(this::selectedLocation.isInitialized && this::selectedCinema.isInitialized && this::selectedSeat.isInitialized){

            viewModel.validateSelections(selectedLocation,selectedCinema,selectedSeat)
        }else{
            Toast.makeText(this@BookTicketActivity,"Please make a valid selection",Toast.LENGTH_SHORT).show()
        }
    }
}