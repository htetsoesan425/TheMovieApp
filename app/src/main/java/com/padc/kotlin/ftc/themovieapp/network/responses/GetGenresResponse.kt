package com.padc.kotlin.ftc.themovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.kotlin.ftc.themovieapp.data.vos.GenreVO

data class GetGenresResponse(
    @SerializedName("genres")
    val genres: List<GenreVO>
)