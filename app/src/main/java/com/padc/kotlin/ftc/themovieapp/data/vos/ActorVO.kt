package com.padc.kotlin.ftc.themovieapp.data.vos

import com.google.gson.annotations.SerializedName

data class ActorVO(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("known_for")
    val known_for: List<MovieVO>?,
    @SerializedName("known_for_department")
    val knownForDepartment: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("cast_id")
    val castId: Int?,
    @SerializedName("credit_id")
    val creditId: String?,
    @SerializedName("character")
    val character: String?,
    @SerializedName("order")
    val order: Int?,


    )