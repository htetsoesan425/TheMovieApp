package com.padc.kotlin.ftc.themovieapp.data.vos

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class ProductionCompanyVO(
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int?,

    @SerializedName("logo_path")
    @ColumnInfo(name = "logo_path")
    val logoPath: String?,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String?,

    @SerializedName("origin_country")
    @ColumnInfo(name = "origin_country")
    val originCountry: String?,
)