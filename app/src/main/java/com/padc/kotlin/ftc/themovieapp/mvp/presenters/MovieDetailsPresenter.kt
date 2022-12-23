package com.padc.kotlin.ftc.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import com.padc.kotlin.ftc.themovieapp.mvp.views.MovieDetailsView

interface MovieDetailsPresenter : IBasePresenter {
    fun initView(view: MovieDetailsView)
    fun onUiReadyInMovieDetails(owner: LifecycleOwner, movieId: Int)
    fun onTapBack()

}