package com.example.android.themoviesapp.MovieDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android.themoviesapp.Adapters.MoviePosterViewPagerAdapter
import com.example.android.themoviesapp.BookTicket.BookTicketActivity
import com.example.android.themoviesapp.Database.MoviesDatabase
import com.example.android.themoviesapp.Others.ApiPreferences
import com.example.android.themoviesapp.Others.DEFAULT_ERROR_MESSAGE
import com.example.android.themoviesapp.Others.MOVIE_TRAILER_LINK
import com.example.android.themoviesapp.Others.getDate2
import com.example.android.themoviesapp.R
import com.example.android.themoviesapp.Retrofit.GetMovieDetailsResponse
import com.example.android.themoviesapp.Retrofit.Poster
import com.example.android.themoviesapp.databinding.ActivityMovieDetailsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.DecimalFormat


class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMovieDetailsBinding
    private lateinit var viewModel: MovieDetailsViewModel

    private lateinit var viewPagerAdapter:MoviePosterViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeToolbar()

        initializeViewPager()

        initializeOnClickListeners()

        initializeViewModel()

        initializeObservers()







        callGetMovieDetailsScript()

        callGetMoviePosters()
    }




    /*
   * Assign ViewModel to activity*/
    private fun initializeViewModel(){

        val application = requireNotNull(this).application
        val bundle = intent?.extras
        val dataSource = MoviesDatabase.getInstance(application)
        val apiPreferences = ApiPreferences(application)
        val viewModelFactory = MovieDetailsViewModelFactory(bundle,dataSource,apiPreferences)

        viewModel = ViewModelProvider(
            this, viewModelFactory).get(MovieDetailsViewModel::class.java)
    }

    private fun initializeToolbar(){
        setSupportActionBar(binding.toolbar)
        binding.toolbarTitle.text = intent?.extras?.getString("MOVIE_NAME")
        binding.navIcon.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initializeOnClickListeners(){
        binding.viewTrailerButton.setOnClickListener {

            callGetMovieTrailers()
        }

        binding.bookMovieTicketCL.setOnClickListener {
            viewModel.navigateToBookTicketScreen()
        }
    }

    private fun initializeViewPager(){
        viewPagerAdapter = MoviePosterViewPagerAdapter(this)
        binding.movieImagesViewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.swipeableImagesTabLayout,binding.movieImagesViewPager,object :
            TabLayoutMediator.TabConfigurationStrategy {
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {

            }

        }).attach()
    }

    private fun initializeObservers(){

        //for movie details live data
        viewModel.movieDetails.observe(this,{

            binding.materialPorgressBar.visibility = View.GONE
            if(it!=null){

                fillData(it)
            }else {

                Toast.makeText(this@MovieDetailsActivity, DEFAULT_ERROR_MESSAGE,Toast.LENGTH_SHORT).show()
            }
        })

        //for movie posters live data
        viewModel.moviePosters.observe(this,{

            if(it.posters.isNotEmpty()?:false){

                if(it.posters.size > 5) {
                    viewPagerAdapter.updateList(it.posters.take(5) as ArrayList<Poster>)
                }else{
                    viewPagerAdapter.updateList(it.posters)
                }

            }else{
                Toast.makeText(this@MovieDetailsActivity,DEFAULT_ERROR_MESSAGE,Toast.LENGTH_SHORT).show()
            }
        })

        //for movie tailer urls live data
        viewModel.movieUrls.observe(this,{

            binding.materialPorgressBar.visibility = View.GONE
            if(it!=null && it.urlResults.isNotEmpty()){


                /*
                *
                * always get the first url link*/
                if(it.urlResults.size>=1){
                    val urlKey = it?.urlResults?.get(0).key?:""
                    openVideoUrl(urlKey)
                }else{
                    Toast.makeText(this@MovieDetailsActivity, DEFAULT_ERROR_MESSAGE,Toast.LENGTH_SHORT).show()
                }
            }else {

                Toast.makeText(this@MovieDetailsActivity, DEFAULT_ERROR_MESSAGE,Toast.LENGTH_SHORT).show()
            }
        })

        //for navigating to book ticket screen
        viewModel.bundleData.observe(this,{

            val moviDetails:GetMovieDetailsResponse? = it?.getParcelable("MOVIE_DETAILS")
            if(moviDetails!=null){

                navigateToBookTicketScreen(it)
            }else{
                Toast.makeText(this@MovieDetailsActivity, "Unable to fetch data.Something went wrong",Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun navigateToBookTicketScreen(bundle: Bundle) {

        val movieDetails: GetMovieDetailsResponse? = bundle?.getParcelable("MOVIE_DETAILS")
        val i =Intent(this@MovieDetailsActivity,BookTicketActivity::class.java)
        i.putExtra("MOVIE_DETAILS",movieDetails)
        startActivity(i)
    }

    /*
    * call get movie details script*/
    private fun callGetMovieDetailsScript() {
        binding.materialPorgressBar.visibility = View.VISIBLE
       viewModel.callMovieDetailsScript()
    }

    /*
    * call movie posters script
    * */
    private fun callGetMoviePosters(){
        viewModel.callMoviePostersScript()
    }


    private fun callGetMovieTrailers(){
        binding.materialPorgressBar.visibility = View.VISIBLE
        viewModel.callMovieTrailerScript()


    }

    private fun openVideoUrl(key:String){
        val url = "${MOVIE_TRAILER_LINK}${key}"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)

    }

    private fun fillData(data:GetMovieDetailsResponse){


        binding.movieTitle.text = "${data?.originalTitle?:""}"

        binding.dateValue.text = "${data?.releaseDate?.getDate2()}"

        binding.overviewValue.text = "${data?.overview?:""}"

        //displaying genres list
        var genresList = data.genres?.map { it.name } as ArrayList
        val genresStringBuilder = StringBuilder("")

        genresList.forEachIndexed { index, s ->

            if(index == genresList.size - 1){
                genresStringBuilder.append(s)
            }else{
                genresStringBuilder.append(s).append(", ")
            }
        }
        binding.genresValues.text = "${genresStringBuilder}"

        //display popularityRating
        val f = DecimalFormat("0.0")
        binding.popularityRating.rating = "${f.format(data?.voteAverage)}".toFloat()


    }
}