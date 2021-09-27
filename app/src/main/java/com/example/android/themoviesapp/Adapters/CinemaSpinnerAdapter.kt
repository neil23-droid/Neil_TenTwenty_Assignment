package com.example.android.themoviesapp.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.example.android.themoviesapp.DataModels.CinemaData
import com.example.android.themoviesapp.DataModels.LocationData
import com.example.android.themoviesapp.R

class CinemaSpinnerAdapter(context: Context, @LayoutRes private val layoutResource: Int, private var cinemaData: ArrayList<CinemaData>):
    ArrayAdapter<CinemaData>(context, layoutResource, cinemaData) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createViewFromResource(position, convertView, parent)
    }

    fun updateList(cinemaData: ArrayList<CinemaData>){
        this.cinemaData = cinemaData
        notifyDataSetChanged()
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val tv = view as TextView
        if (position == 0) {
            // Set the hint text color gray
            tv.text = "Select Cinema"
            tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
            tv.setTextColor(Color.GRAY)
        } else {
            tv.text = cinemaData[position].cinemaName
            tv.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            tv.setTextColor(Color.BLACK)
        }
        return view
    }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup?): View{
        val view: TextView = convertView as TextView? ?: LayoutInflater.from(context).inflate(layoutResource, parent, false) as TextView

        if(position == 0){

            view.text = cinemaData[position].cinemaName
            view.setTextColor(ContextCompat.getColor(view!!.context, R.color.black))
            view.textAlignment = View.TEXT_ALIGNMENT_TEXT_START

        }else {

            view.text = cinemaData[position].cinemaName
            view.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        }
        return view
    }

    override fun isEnabled(position: Int): Boolean {
        return position != 0
    }

}
