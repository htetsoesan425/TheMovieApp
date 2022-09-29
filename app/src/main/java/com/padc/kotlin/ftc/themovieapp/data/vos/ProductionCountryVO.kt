package com.padc.kotlin.ftc.themovieapp.data.vos

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class ProductionCountryVO(
    @SerializedName("iso_3166_1")
    @ColumnInfo(name = "iso_3166_1")
    val iso3166_1: String?,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String?
)