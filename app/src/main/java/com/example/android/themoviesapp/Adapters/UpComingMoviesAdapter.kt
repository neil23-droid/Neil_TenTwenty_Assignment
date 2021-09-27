package com.example.android.themoviesapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.themoviesapp.Others.POSTER_BASE_URL
import com.example.android.themoviesapp.Others.getDate2
import com.example.android.themoviesapp.R
import com.example.android.themoviesapp.Retrofit.UpComingMovies
import com.example.android.themoviesapp.databinding.MoviesListItemBinding

class UpComingMoviesAdapter(
    private var context: Context,
    private val onMovieClick:(UpComingMovies)->Unit={}
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var upCommingMoviesList:ArrayList<UpComingMovies> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false
        val itemBinding = MoviesListItemBinding.inflate(inflater, parent, shouldAttachToParentImmediately)

        return UpComingMovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UpComingMovieViewHolder).bindData(upCommingMoviesList.get(position))
    }

    override fun getItemCount(): Int {
        return upCommingMoviesList.size
    }

    fun updateList(data:ArrayList<UpComingMovies>){
        upCommingMoviesList = data
        notifyDataSetChanged()
    }

    inner class UpComingMovieViewHolder(private val itemBinding: MoviesListItemBinding):
        RecyclerView.ViewHolder(itemBinding.root){


            fun bindData(data:UpComingMovies){

                //display image
                Glide
                    .with(context)
                    .load("${POSTER_BASE_URL}${data?.posterPath?:""}")
                    .placeholder(R.drawable.empty_poster)
                    .error(R.drawable.empty_poster)
                    .into(itemBinding.moviePosterIV)

                itemBinding.movieNameTxt.text = "${data.originalTitle?:""}"

                itemBinding.movieReleaseDateTxt.text = "${data.releaseDate?.getDate2()}"

                itemBinding.adultsOnlyTxt.text = if(data.adult){
                    "Adult"
                }else{
                    "Non-Adult"
                }

                /*
                on click listener
                * */

                itemBinding.root.setOnClickListener {
                    onMovieClick(data)
                }


            }




        }
}