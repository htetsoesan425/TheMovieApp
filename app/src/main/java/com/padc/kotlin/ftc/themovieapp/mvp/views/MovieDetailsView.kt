package com.padc.kotlin.ftc.themovieapp.mvp.views

import com.padc.kotlin.ftc.themovieapp.data.vos.ActorVO
import com.padc.kotlin.ftc.themovieapp.data.vos.MovieVO

interface MovieDetailsView : BaseView {
    fun showMovieDetails(movie: MovieVO)
    fun showCreditsByMovie(cast: List<ActorVO>, crew: List<ActorVO>)
    fun navigateBack()
}