package com.padc.kotlin.ftc.themovieapp.data.vos

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class SpokenLanguageVO(
    @SerializedName("iso_639_1")
    @ColumnInfo(name = "iso_639_1")
    val iso639_1: String?,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String?,

    @SerializedName("english_name")
    @ColumnInfo(name = "english_name")
    val englishName: String?
)