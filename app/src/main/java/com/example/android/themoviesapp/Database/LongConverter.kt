package com.example.android.themoviesapp.Database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class LongConverter {
    @TypeConverter
    fun fromString(value: String): ArrayList<Long> {
        val listType = object : TypeToken<ArrayList<Long>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Long>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}