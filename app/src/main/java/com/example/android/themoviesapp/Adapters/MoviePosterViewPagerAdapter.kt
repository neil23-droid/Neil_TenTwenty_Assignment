package com.example.android.themoviesapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.themoviesapp.Others.POSTER_BASE_URL
import com.example.android.themoviesapp.R
import com.example.android.themoviesapp.Retrofit.Poster
import com.example.android.themoviesapp.databinding.MoviePosterItemBinding
import com.example.android.themoviesapp.databinding.MoviesListItemBinding

class MoviePosterViewPagerAdapter(private var context:Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var moviePosterList:ArrayList<Poster> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false
        val itemBinding = MoviePosterItemBinding.inflate(inflater, parent, shouldAttachToParentImmediately)

        return MoviePosterViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MoviePosterViewHolder).bindData(moviePosterList.get(position))
    }

    override fun getItemCount(): Int {

        return moviePosterList.size
    }

    fun updateList(data:ArrayList<Poster>){
        moviePosterList = data
        notifyDataSetChanged()
    }

    inner class MoviePosterViewHolder(val itemBinding: MoviePosterItemBinding):RecyclerView.ViewHolder(itemBinding.root){

        fun bindData(data:Poster){

            Glide
                .with(context)
                .load("${POSTER_BASE_URL}${data.filePath?:""}")
                .placeholder(R.drawable.empty_poster)
                .error(R.drawable.empty_poster)
                .into(itemBinding.moviePoster)
        }

    }
}