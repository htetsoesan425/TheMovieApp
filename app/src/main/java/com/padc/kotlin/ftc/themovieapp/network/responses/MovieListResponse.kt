package com.padc.kotlin.ftc.themovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.kotlin.ftc.themovieapp.data.vos.DateVO
import com.padc.kotlin.ftc.themovieapp.data.vos.MovieVO

data class MovieListResponse(

    @SerializedName("page")
    val page: Int?,

    @SerializedName("dates")
    val dates: DateVO?,

    @SerializedName("results")
    val results: List<MovieVO>?
)