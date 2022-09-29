package com.padc.kotlin.ftc.themovieapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padc.kotlin.ftc.themovieapp.data.vos.ActorVO

data class GetActorsResponse(
    @SerializedName("results")
    val result: List<ActorVO>
)
