package com.padc.kotlin.ftc.themovieapp.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreIdsTypeConverter {
    @TypeConverter
    fun toString(genreList: List<Int>?): String {
        return Gson().toJson(genreList)
    }

    @TypeConverter
    fun toGenreIds(genreListJsonStr: String): List<Int>? {
        val genreIdsListType = object : TypeToken<List<Int>?>() {}.type
        return Gson().fromJson(genreListJsonStr, genreIdsListType)
    }
}