package com.padc.kotlin.ftc.themovieapp.data.vos

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class GenreVO(
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int?,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String
)