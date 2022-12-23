package com.padc.kotlin.ftc.themovieapp.mvp.presenters

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.kotlin.ftc.themovieapp.interactors.MovieInteractor
import com.padc.kotlin.ftc.themovieapp.interactors.MovieInteractorImpl
import com.padc.kotlin.ftc.themovieapp.mvp.views.MovieDetailsView

class MovieDetailsPresenterImpl : ViewModel(), MovieDetailsPresenter {

    // Model
    private val mMovieInteractor: MovieInteractor = MovieInteractorImpl

    // View
    private var mView: MovieDetailsView? = null

    override fun onUiReady(owner: LifecycleOwner) {}

    override fun initView(view: MovieDetailsView) {
        mView = view
    }

    override fun onUiReadyInMovieDetails(owner: LifecycleOwner, movieId: Int) {
        mMovieInteractor.getMovieDetail(movieId = movieId.toString(),
            onFailure = {
                mView?.showError(it)
            })?.observe(owner) {
            it?.let { movieDetails -> mView?.showMovieDetails(movieDetails) }
        }

        mMovieInteractor.getCreditsByMovie(
            movieId = movieId.toString(),
            onSuccess = {
                mView?.showCreditsByMovie(cast = it.first, crew = it.second)
            },
            onFailure = {
                mView?.showError(it)
            }

        )
    }

    override fun onTapBack() {
        mView?.navigateBack()
    }
}