package com.padc.kotlin.ftc.themovieapp.mvp.presenters

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.padc.kotlin.ftc.themovieapp.data.vos.GenreVO
import com.padc.kotlin.ftc.themovieapp.interactors.MovieInteractor
import com.padc.kotlin.ftc.themovieapp.interactors.MovieInteractorImpl
import com.padc.kotlin.ftc.themovieapp.mvp.views.MainView

class MainPresenterImpl : ViewModel(), MainPresenter {

    // View
    var mView: MainView? = null

    // Model
    // private val mMovieIntector: MovieModel = MovieModelImpl

    // Interactor
    private val mMovieIntector: MovieInteractor = MovieInteractorImpl

    // State
    private var mGenres: List<GenreVO>? = null


    override fun initView(view: MainView) {
        mView = view
    }

    override fun onUiReady(owner: LifecycleOwner) {

        // now playing
        mMovieIntector.getNowPlayingMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showNowPlayingMovies(it)
        }

        // popular movies
        mMovieIntector.getPopularMovies {
            mView?.showError(it)
        }?.observe(owner) {
            mView?.showPopularMovies(it)
        }

        //top rated movies
        mMovieIntector.getTopRatedMovies {
            mView?.showError(it)

        }?.observe(owner) {
            mView?.showTopRatedMovies(it)
        }

        //genres
        mMovieIntector.getGenres(
            onSuccess = {
                mGenres = it
                mView?.showGenres(it)
                it.firstOrNull()?.id?.let { firstGenreId -> onTapGenre(firstGenreId) }
            },
            onFailure = {
                mView?.showError(it)
            }
        )

        //actors
        mMovieIntector.getActors(
            onSuccess = {
                Log.d("TAG", "requestData: $it")
                mView?.showActors(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapMovieFromBanner(movieId: Int) {
        mView?.navigateToMovieDetailScreen(movieId = movieId)
    }

    override fun onTapMovie(movieId: Int) {
        mView?.navigateToMovieDetailScreen(movieId = movieId)
    }

    override fun onTapMovieFromShowCase(movieId: Int) {
        mView?.navigateToMovieDetailScreen(movieId = movieId)
    }

    override fun onTapGenre(genrePosition: Int) {
        mGenres?.getOrNull(genrePosition)?.id?.let { genreId ->
            mMovieIntector.getMoviesByGenre(genreId = genreId.toString(), onSuccess = {
                mView?.showMoviesByGenre(it)
            },
                onFailure = {
                    mView?.showError(it)
                })


        }
    }
}