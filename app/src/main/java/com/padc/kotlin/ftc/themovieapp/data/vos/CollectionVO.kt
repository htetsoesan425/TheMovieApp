package com.padc.kotlin.ftc.themovieapp.data.vos

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class CollectionVO(
    @SerializedName("id")
    @ColumnInfo(name = "id")
    val id: Int?,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String?,

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    val poster_path: String?,

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    val backdrop_path: String?,
)
